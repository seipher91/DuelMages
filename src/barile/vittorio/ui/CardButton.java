package barile.vittorio.ui;

import barile.vittorio.utils.Resources;

import javax.swing.*;
import java.awt.*;

public class CardButton extends JButton {
    private String name;
    private final Image shell;
    private final Image element;

    public CardButton(int width, int height, String name, String element) {
        super();
        this.setBackground(null);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setOpaque(false);

        this.setLayout(null);
        this.setSize(width, height);
        this.setLocation(0,0);

        this.shell = Resources.getImage("assets/images/card_shell.png").getSubimage(0, 0, 283, 406);
        this.element = Resources.getImage(element);

        this.name = name;
        JLabel label = new JLabel(this.name, SwingConstants.CENTER);
        label.setForeground(Color.white);
        label.setSize(width, 60);
        label.setLocation(0, 145);

        add(label);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(this.element,
                20, 20,
                100, 100,
                this);

        g.drawImage(this.shell,
                0, 0,
                getWidth(), getHeight(),
                this);
    }

}
