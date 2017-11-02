package barile.vittorio.ui;

import barile.vittorio.engine.Spell;
import barile.vittorio.entites.Mage;
import barile.vittorio.ui.interfaces.OnChoiceListener;
import barile.vittorio.ui.interfaces.OnGameControlChange;
import barile.vittorio.ui.interfaces.OnSpellListener;
import barile.vittorio.ui.interfaces.OnVitalityEventListener;
import barile.vittorio.utils.Resources;
import barile.vittorio.utils.Sound;
import lombok.Setter;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static barile.vittorio.engine.Spell.*;
import static barile.vittorio.ui.Hud.PLAYER_1;
import static barile.vittorio.ui.Hud.PLAYER_2;

/**
 * Campo di gioco
 * @author Vittorio
 */
public class GameField extends JPanel implements OnSpellListener, OnVitalityEventListener, OnGameControlChange {
    private MagePlayer mage_1, mage_2;
    private Hud hud;

    private Clip theme, end;

    @Setter
    private OnChoiceListener choice;

    public GameField() {
        setSize(MainWindow.LARGHEZZA, 400);
        setLocation(0, 0);
        setLayout(null);

        init();
        start();
    }

    /**
     * Inizializza le entita' del campo di gioco
     */
    private void init() {
        mage_1 = new MagePlayer("Paladino",
                "Alleanza",
                "assets/images/mage_bronze_mod.png",
                this);
        mage_2 = new MagePlayer("Shamano",
                "Orda",
                "assets/images/mage_green_mod.png",
                this) {
            protected Graphics getComponentGraphics(final Graphics g) {
                return horizontalFlip(super.getComponentGraphics(g), getWidth());
            }
        };

        mage_2.setAsEnemy();

        add(mage_1);
        mage_1.setLocation(150, 0);

        add(mage_2);
        mage_2.setLocation(250, 0);

        hud = new Hud();
        add(hud);

        Background background = new Background(MainWindow.LARGHEZZA, 400);
        add(background);

        theme = Sound.getClip("assets/sounds/battle_theme.wav");
    }

    /**
     * Da' inizio alle routine di gioco
     */
    private void start() {
        Thread engine = new Thread(new Engine());
        engine.start();

        if(choice != null) choice.grantChioce();

        Sound.setVolume(theme, 40f);
        theme.loop(Clip.LOOP_CONTINUOUSLY);
        theme.start();
    }

    /**
     * Definisce il riavvio della routine di gioco
     */
    @Override
    public void reset() {
        mage_1.obtainHeal(Mage.MAX_LIFE);
        mage_2.obtainHeal(Mage.MAX_LIFE);

        mage_1.restore();
        mage_2.restore();

        hud.restore();
        choice.grantChioce();

        if(end != null) end.stop();
        if(!theme.isRunning()) theme.start();
    }

    @Override
    public void pause() {

    }

    /**
     * Permette di rovesciare la rappresentazione di una componente visiva
     */
    private static Graphics horizontalFlip(final Graphics g, final int width) {
        final Graphics2D g2d = (Graphics2D) g;
        final AffineTransform tx = g2d.getTransform();
        tx.scale(-1.0, 1.0);
        tx.translate(-width, 0);
        g2d.setTransform(tx);
        return g2d;
    }

    /**
     * Aggiorna visivamente il campo di gioco
     */
    private synchronized void updateEnvironment() {
        //mage_1.repaint();
        //mage_2.repaint();

        repaint();
    }

    /**
     * Cattura gli eventi di magie offensive castate
     * @param spell Magia Lanciata
     */
    @Override
    public void onSpellCast(Spell spell) {
        mage_1.addStatus(MagePlayer.STATUS_ATTACK);
        mage_2.addStatus(MagePlayer.STATUS_ATTACK);

        Spell attack = mage_1.execAttack(spell);
        switch (attack.getType()) {
            case FIRE_TYPE:
                Sound.getClip("assets/sounds/fireball_sound.wav").start();
                break;
            case FROST_TYPE:
                Sound.getClip("assets/sounds/frostbolt_sound.wav").start();
                break;
            case ARCANE_TYPE:
                Sound.getClip("assets/sounds/arcane_blast_sound.wav").start();
                break;
        }

        Spell defense = mage_2.execAttack(
                Spell.builder()
                        .type(Spell.getRandomType())
                        .build());

        switch (attack.tryOffense(defense)) {
            case SPELL_DRAW:
                hud.setStatus(Hud.STATUS_DRAW, attack.getType(), defense.getType());
                break;
            case SPELL_WIN:
                mage_2.obtainDamage(attack.getPower());

                hud.setStatus(Hud.STATUS_WIN, attack.getType(), defense.getType());
                hud.addDamage(PLAYER_2, attack.getPower());

                mage_2.addStatus(MagePlayer.STATUS_DAMAGED);
                break;
            case SPELL_LOSE:
                mage_1.obtainDamage(defense.getPower());

                hud.setStatus(Hud.STATUS_LOSE, attack.getType(), defense.getType());
                hud.addDamage(PLAYER_1, defense.getPower());

                mage_1.addStatus(MagePlayer.STATUS_DAMAGED);
                break;
        }
    }

    /**
     * Cattura l'evento di morte di un Mago
     * @param name Nome del mago morto in battaglia
     */
    @Override
    public void onDeath(String name) {
        choice.denyChioce();
        theme.stop();

        if(mage_1.getName().equals(name)) {
            hud.setStatus(Hud.STATUS_GAME_OVER);
            mage_1.addStatus(MagePlayer.STATUS_LOSE);
            mage_2.addStatus(MagePlayer.STATUS_WIN);

            end = Sound.getClip("assets/sounds/game_over_sound.wav");
        } else {
            hud.setStatus(Hud.STATUS_CHECK_MATE);
            mage_1.addStatus(MagePlayer.STATUS_WIN);
            mage_2.addStatus(MagePlayer.STATUS_LOSE);

            end = Sound.getClip("assets/sounds/win_sound.wav");
        }

        if(end != null) end.start();
    }

    /**
     * Routine di gioco scansionato ogni 220 millisecondi
     */
    public class Engine implements Runnable {
        public void run() {
            while (true) {
                updateEnvironment();

                try {
                    // thread to sleep for 220 milliseconds
                    Thread.sleep(220);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    private class Background extends JPanel {
        private final Image img;

        public Background(int width, int height) {
            super();
            this.setBackground(null);
            this.setSize(width, height);
            this.setLocation(0,0);

            this.img = Resources.getImage("assets/images/environment_sunset.gif");
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(this.img,
                    0, 0,
                    getWidth(), getHeight(),
                    this);
        }
    }

}
