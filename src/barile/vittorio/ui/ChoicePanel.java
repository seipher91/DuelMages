package barile.vittorio.ui;

import barile.vittorio.engine.Spell;
import barile.vittorio.entites.Mage;
import barile.vittorio.ui.interfaces.OnChoiceListener;
import barile.vittorio.ui.interfaces.OnGameControlChange;
import barile.vittorio.ui.interfaces.OnSpellListener;
import barile.vittorio.utils.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

public class ChoicePanel extends JPanel implements ActionListener, OnChoiceListener {
    private Background background;
    private OnSpellListener listener;
    private OnGameControlChange gameControl;

    private boolean active;

    public ChoicePanel(OnSpellListener listener, OnGameControlChange gameControl) {
        setSize(MainWindow.LARGHEZZA, 380);
        setLocation(0, 400);

        this.listener = listener;
        this.gameControl = gameControl;

        setLayout(null);

        active = true;

        init();
    }

    private void init() {
        //setBackground(new Color(96,71,35));
        background = new Background(getWidth(), 500);
        background.setLocation(0, -130);


        CardButton fire = new CardButton(145, 220,
                "Fireball", "assets/images/fireball.jpg", "<html>Vince su Arcane<BR>Perde contro Frost</html>");
        fire.setLocation(90, 65);
        fire.addActionListener(this);
        fire.setActionCommand("fire");


        CardButton frost = new CardButton(145, 220,
                "Frostbolt", "assets/images/frostbolt.jpg", "<html>Vince su Fireball<BR>Perde contro Arcane</html>");
        frost.setLocation(245, 65);
        frost.addActionListener(this);
        frost.setActionCommand("frost");


        CardButton arcane = new CardButton(145, 220,
                "Arcane Blast", "assets/images/arcaneblast.jpg", "<html>Vince su Frost<BR>Perde contro Fire</html>");
        arcane.setLocation(395, 65);
        arcane.addActionListener(this);
        arcane.setActionCommand("arcane");

        CardButton resume = new CardButton(145, 220,
                "Resume", "assets/images/heartstone.png", "Gioca di nuovo");
        resume.setLocation(550, 65);
        resume.addActionListener(this);
        resume.setActionCommand("resume");

        add(fire);
        add(frost);
        add(arcane);

        add(resume);

        add(background);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();
        if(this.listener == null) return;

        switch (action) {
            case "fire":
            case "frost":
            case "arcane":
                if (!active) return;
        }

        switch (action) {
            case "fire":
                this.listener.onSpellCast(
                        Spell.builder()
                                .type(Spell.FIRE_TYPE)
                                .build());

                break;
            case "frost":
                this.listener.onSpellCast(
                        Spell.builder()
                                .type(Spell.FROST_TYPE)
                                .build());

                break;
            case "arcane":
                this.listener.onSpellCast(
                        Spell.builder()
                                .type(Spell.ARCANE_TYPE)
                                .build());
                break;

            case "resume":
                this.gameControl.reset();
                break;
        }
    }

    @Override
    public void grantChioce() {
        active = true;
    }

    @Override
    public void denyChioce() {
        active = false;
    }

    private class Background extends JPanel {
        private final Image img;

        public Background(int width, int height) {
            super();
            this.setBackground(null);
            this.setSize(width, height);
            this.setLocation(0,0);

            this.img = Resources.getImage("assets/images/action_bar.jpg");
        }

        @Override
        protected Graphics getComponentGraphics(Graphics g) {
            //return super.getComponentGraphics(g);
            return verticalFlip(super.getComponentGraphics(g), getHeight());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(this.img,
                    0, 0,
                    getWidth(), getHeight(),
                    this);
        }

        private Graphics verticalFlip(final Graphics g, final int height) {
            final Graphics2D g2d = (Graphics2D) g;
            final AffineTransform tx = g2d.getTransform();
            tx.scale(1.0, -1.0);
            tx.translate(0, -height);
            g2d.setTransform(tx);
            return g2d;
        }

    }
}
