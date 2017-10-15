package barile.vittorio.ui;

import barile.vittorio.utils.Resources;

import javax.swing.*;
import java.awt.*;

public class CardButton extends JButton {
    private String name;
    private String function;
    private final Image shell;
    private final Image element;

    public CardButton(int width, int height, String name, String element, String function) {
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
        JLabel label_name = new JLabel(this.name, SwingConstants.CENTER);
        label_name.setForeground(Color.white);
        label_name.setSize(width, 60);
        label_name.setLocation(0, 85);

        this.function = function;
        JLabel label_function = new JLabel(this.function, SwingConstants.CENTER);
        label_function.setForeground(Color.white);
        label_function.setSize(width, 60);
        label_function.setLocation(0, 140);

        add(label_name);
        add(label_function);
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
