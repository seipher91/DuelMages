package barile.vittorio.ui;

import barile.vittorio.ui.interfaces.OnVitalityListener;
import barile.vittorio.utils.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Vittorio on 08/10/2017.
 */
public class Hud extends JPanel implements OnVitalityListener {
    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;

    private BufferedImage img;
    private BufferedImage layout;

    private BufferedImage lifebar_1;
    private BufferedImage lifebar_2;

    private final int max_life = 250;

    private int player_1_life;
    private int player_2_life;

    public Hud() {
        super();
        setSize(MainWindow.LARGHEZZA, 100);
        setLocation(0, 0);

        setBackground(null);
        setOpaque(false);
        setLayout(null);

        this.img = Resources.getImage("assets/images/hud_lifebar.png");
        this.layout = this.img.getSubimage(4, 4, 318, 42);

        this.lifebar_1 = this.img.getSubimage(138, 68, 99, 4);
        this.lifebar_2 = this.img.getSubimage(138, 58, 99, 4);

        restoreLifes();
    }

    private void restoreLifes() {
        this.player_1_life = max_life;
        this.player_2_life = max_life;
    }

    @Override
    public void addDamage(int player, int damage) {
        switch (player) {
            case PLAYER_1:
                this.player_1_life -= (max_life/100)*damage;
                if(this.player_1_life <= 0) this.player_1_life = 0;
                break;
            case PLAYER_2:
                this.player_2_life -= (max_life/100)*damage;
                if(this.player_2_life <= 0) this.player_2_life = 0;
                break;
        }
    }

    @Override
    public void addHeal(int player, int heal) {
        switch (player) {
            case PLAYER_1:
                this.player_1_life -= (max_life/100)*heal;
                if(this.player_1_life >= max_life) this.player_1_life = max_life;
                break;
            case PLAYER_2:
                this.player_2_life -= (max_life/100)*heal;
                if(this.player_2_life >= max_life) this.player_2_life = max_life;
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(layout,
                0, 0,
                getWidth(), getHeight(),
                this);

        g.drawImage(lifebar_1,
                100,43,
                player_1_life,9,
                this);

        g.drawImage(lifebar_2,
                705,43,
                -player_2_life,9,
                this);

    }
}
