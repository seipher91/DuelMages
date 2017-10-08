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

        JButton fire = new JButton("Fire");
        fire.setSize(240, 150);
        fire.setLocation(16, 10);
        fire.addActionListener(this);
        fire.setActionCommand("fire");
        fire.setIcon(new ImageIcon(""));




        JButton frost = new JButton("Frost");
        frost.setSize(240, 150);
        frost.setLocation(276, 10);
        frost.addActionListener(this);
        frost.setActionCommand("frost");

        JButton arcane = new JButton("Arcane");
        arcane.setSize(240, 150);
        arcane.setLocation(536, 10);
        arcane.addActionListener(this);
        arcane.setActionCommand("arcane");

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
