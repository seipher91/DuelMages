package barile.vittorio.ui;

import barile.vittorio.engine.Spell;
import barile.vittorio.ui.interfaces.OnVitalityListener;
import barile.vittorio.utils.Resources;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Vittorio on 08/10/2017.
 */
public class Hud extends JPanel implements OnVitalityListener {
    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;

    public static final int STATUS_FIGHT = 0;
    public static final int STATUS_WIN = 1;
    public static final int STATUS_LOSE = 2;
    public static final int STATUS_DRAW = 3;
    public static final int STATUS_GAME_OVER = 4;
    public static final int STATUS_CHECK_MATE = 5;

    private BufferedImage img;
    private BufferedImage layout;
    private BufferedImage vs;

    private BufferedImage lifebar_1;
    private BufferedImage lifebar_2;

    private BufferedImage fight;
    private BufferedImage win;
    private BufferedImage lose;
    private BufferedImage draw;
    private BufferedImage game_over;
    private BufferedImage check_mate;

    private BufferedImage fire;
    private BufferedImage frost;
    private BufferedImage arcane;

    private BufferedImage spell_1;
    private BufferedImage spell_2;

    private final int max_life = 250;

    private int player_1_life;
    private int player_2_life;

    @Setter
    private int status;

    public Hud() {
        super();
        setSize(MainWindow.LARGHEZZA, 300);
        setLocation(0, 0);

        setBackground(null);
        setOpaque(false);
        setLayout(null);

        this.img = Resources.getImage("assets/images/hud_lifebar.png");
        this.layout = this.img.getSubimage(4, 4, 318, 42);

        this.vs = Resources.getImage("assets/images/Vs.png");

        this.lifebar_1 = this.img.getSubimage(138, 68, 99, 4);
        this.lifebar_2 = this.img.getSubimage(138, 58, 99, 4);

        this.fight = this.img.getSubimage(244,184,126,30);
        this.win = this.img.getSubimage(44,250,69,30);
        this.lose = this.img.getSubimage(269,319,90,30);
        this.draw = this.img.getSubimage(3,318,113,30);
        this.game_over = this.img.getSubimage(2,285,211,30);
        this.check_mate = this.img.getSubimage(1,217,229,30);

        this.fire = Resources.getImage("assets/images/fireball.jpg");
        this.frost = Resources.getImage("assets/images/frostbolt.jpg");
        this.arcane = Resources.getImage("assets/images/arcaneblast.jpg");

        this.status = STATUS_FIGHT;

        restoreLifes();
    }

    public void restore() {
        restoreLifes();
        this.status = STATUS_FIGHT;

        spell_1 = null;
        spell_2 = null;
    }

    private void restoreLifes() {
        this.player_1_life = max_life;
        this.player_2_life = max_life;
    }

    public void setStatus(int status, String spell_1, String spell_2) {
        switch (this.status) {
            case STATUS_GAME_OVER:
            case STATUS_CHECK_MATE:
                break;
            default:
                setStatus(status);
                break;
        }

        switch (spell_1) {
            case Spell.FIRE_TYPE:
                this.spell_1 = this.fire;
                break;
            case Spell.FROST_TYPE:
                this.spell_1 = this.frost;
                break;
            case Spell.ARCANE_TYPE:
                this.spell_1 = this.arcane;
                break;
        }


        switch (spell_2) {
            case Spell.FIRE_TYPE:
                this.spell_2 = this.fire;
                break;
            case Spell.FROST_TYPE:
                this.spell_2 = this.frost;
                break;
            case Spell.ARCANE_TYPE:
                this.spell_2 = this.arcane;
                break;
        }
    }

    @Override
    public void addDamage(int player, int damage) {
        switch (player) {
            case PLAYER_1:
                this.player_1_life -= (max_life*damage)/100;
                if(this.player_1_life <= 0) this.player_1_life = 0;
                break;
            case PLAYER_2:
                this.player_2_life -= (max_life*damage)/100;
                if(this.player_2_life <= 0) this.player_2_life = 0;
                break;
        }
    }

    @Override
    public void addHeal(int player, int heal) {
        switch (player) {
            case PLAYER_1:
                this.player_1_life -= (max_life*heal)/100;
                if(this.player_1_life >= max_life) this.player_1_life = max_life;
                break;
            case PLAYER_2:
                this.player_2_life -= (max_life*heal)/100;
                if(this.player_2_life >= max_life) this.player_2_life = max_life;
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(layout,
                0, 0,
                getWidth(), 100,
                this);

        g.drawImage(vs,
                370, 0,
                75, 75,
                this);

        g.drawImage(lifebar_1,
                100,43,
                player_1_life,9,
                this);

        g.drawImage(lifebar_2,
                705,43,
                -player_2_life,9,
                this);

        BufferedImage result = null;
        switch(status) {
            case STATUS_FIGHT:
                result = fight;
                break;
            case STATUS_GAME_OVER:
                result = game_over;
                break;
            case STATUS_CHECK_MATE:
                result = check_mate;
                break;
            case STATUS_WIN:
                result = win;
                break;
            case STATUS_LOSE:
                result = lose;
                break;
            case STATUS_DRAW:
                result = draw;
                break;
        }

        if(result != null)
            g.drawImage(result,
                (getWidth()/2)-(result.getWidth()/2), 150,
                    result.getWidth(), result.getHeight(),
                this);

        if(spell_1 != null)
            g.drawImage(spell_1,
                175,150,
                spell_1.getWidth(), spell_1.getHeight(),
                this);


        if(spell_2 != null)
            g.drawImage(spell_2,
                    getWidth()-250,150,
                    spell_2.getWidth(), spell_2.getHeight(),
                    this);

    }
}
