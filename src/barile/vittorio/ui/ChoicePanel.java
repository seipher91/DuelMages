package barile.vittorio.ui;

import barile.vittorio.engine.Spell;
import barile.vittorio.ui.interfaces.OnSpellListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoicePanel extends JPanel implements ActionListener {

    private OnSpellListener listener;

    public ChoicePanel(OnSpellListener listener) {
        setSize(MainWindow.LARGHEZZA, 200);
        setLocation(0, 400);

        this.listener = listener;
        setLayout(null);

        init();
    }

    private void init() {
        setBackground(new Color(96,71,35));

        JButton fire = new JButton();
        fire.setSize(64, 63);
        fire.setLocation(152, 50);
        fire.addActionListener(this);
        fire.setActionCommand("fire");
        fire.setIcon(new ImageIcon("assets/images/fireball.jpg"));



        JButton frost = new JButton();
        frost.setSize(64, 63);
        frost.setLocation(368, 50);
        frost.addActionListener(this);
        frost.setActionCommand("frost");
        frost.setIcon(new ImageIcon("assets/images/frostbolt.jpg"));

        JButton arcane = new JButton();
        arcane.setSize(64, 63);
        arcane.setLocation(554, 50);
        arcane.addActionListener(this);
        arcane.setActionCommand("arcane");
        arcane.setIcon(new ImageIcon("assets/images/arcaneblast.jpg"));

        add(fire);
        add(frost);
        add(arcane);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();
        if(this.listener == null) return;

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
        }
    }

}
