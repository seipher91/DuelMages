package barile.vittorio.ui;

import barile.vittorio.engine.Spell;
import barile.vittorio.entites.Mage;
import barile.vittorio.ui.interfaces.OnSpellListener;
import barile.vittorio.utils.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static barile.vittorio.engine.Spell.SPELL_DRAW;
import static barile.vittorio.engine.Spell.SPELL_LOSE;
import static barile.vittorio.engine.Spell.SPELL_WIN;
import static barile.vittorio.ui.Hud.PLAYER_1;
import static barile.vittorio.ui.Hud.PLAYER_2;

public class GameField extends JPanel implements OnSpellListener {
    private MagePlayer mage_1, mage_2;
    private Hud hud;

    public GameField() {
        setSize(MainWindow.LARGHEZZA, 400);
        setLocation(0, 0);
        setLayout(null);

        init();
    }

    private void init() {
        mage_1 = new MagePlayer("Paladino", "Alleanza", "assets/images/mage_bronze.png");
        mage_2 = new MagePlayer("Shamano", "Orda", "assets/images/mage_green.png") {
            protected Graphics getComponentGraphics(final Graphics g) {
                return horizontalFlip(super.getComponentGraphics(g), getWidth());
            }
        };

        mage_2.setAsEnemy();

        add(mage_1);
        mage_1.setLocation(150, 230);

        add(mage_2);
        mage_2.setLocation(250, 230);

        hud = new Hud();
        add(hud);

        Background background = new Background(MainWindow.LARGHEZZA, 400);
        add(background);

        Thread engine = new Thread(new Engine());
        engine.start();
    }

    private static Graphics horizontalFlip(final Graphics g, final int width) {
        final Graphics2D g2d = (Graphics2D) g;
        final AffineTransform tx = g2d.getTransform();
        tx.scale(-1.0, 1.0);
        tx.translate(-width, 0);
        g2d.setTransform(tx);
        return g2d;
    }

    private synchronized void updateEnvironment() {
        //mage_1.repaint();
        //mage_2.repaint();

        repaint();
    }

    @Override
    public void onSpellCast(Spell spell) {
        mage_1.setStatus(MagePlayer.STATUS_ATTACK);
        mage_2.setStatus(MagePlayer.STATUS_ATTACK);

        Spell attack = mage_1.execAttack(spell);

        Spell defense = mage_2.execAttack(
                Spell.builder()
                        .type(Spell.getRandomType())
                        .build());

        switch (attack.tryOffense(defense)) {
            case SPELL_DRAW:
                break;
            case SPELL_WIN:
                mage_2.obtainDamage(attack.getPower());

                hud.addDamage(PLAYER_2, attack.getPower());
                mage_2.setStatus(MagePlayer.STATUS_DAMAGED);
                break;
            case SPELL_LOSE:
                mage_1.obtainDamage(defense.getPower());

                hud.addDamage(PLAYER_1, defense.getPower());
                mage_1.setStatus(MagePlayer.STATUS_DAMAGED);
                break;
        }

        if (mage_1.getLifePoints() <= 0) {
            mage_2.setStatus(MagePlayer.STATUS_WIN);
            mage_1.setStatus(MagePlayer.STATUS_LOSE);
        }
        if (mage_2.getLifePoints() <=0) {
            mage_1.setStatus(MagePlayer.STATUS_WIN);
            mage_2.setStatus(MagePlayer.STATUS_LOSE);
        }
    }

    public class Engine implements Runnable {
        public void run() {
            while (true) {
                updateEnvironment();

                try {
                    // thread to sleep for 250 milliseconds
                    Thread.sleep(250);
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
