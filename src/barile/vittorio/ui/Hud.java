package barile.vittorio.ui;

import barile.vittorio.utils.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Vittorio on 08/10/2017.
 */
public class Hud extends JPanel {
    private BufferedImage img;
    private BufferedImage layout;

    public Hud() {
        super();
        setSize(MainWindow.LARGHEZZA, 100);
        setLocation(0, 0);

        setBackground(null);
        setOpaque(false);
        setLayout(null);

        this.img = Resources.getImage("assets/images/hud_lifebar.png");
        this.layout = this.img.getSubimage(4, 4, 318, 42);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(layout,
                0, 0,
                getWidth(), getHeight(),
                this);

    }
}
