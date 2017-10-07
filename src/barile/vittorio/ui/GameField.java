package barile.vittorio.ui;

import barile.vittorio.engine.Spell;
import barile.vittorio.entites.Mage;
import barile.vittorio.ui.interfaces.OnSpellListener;

import javax.swing.*;
import java.awt.*;

import static barile.vittorio.engine.Spell.SPELL_DRAW;
import static barile.vittorio.engine.Spell.SPELL_LOSE;
import static barile.vittorio.engine.Spell.SPELL_WIN;

public class GameField extends JPanel implements OnSpellListener {
    private Mage mage_1, mage_2;

    public GameField() {
        setSize(MainWindow.LARGHEZZA, 400);
        setLocation(0, 0);

        init();
    }

    private void init() {
        setBackground(new Color(61,205,66));

        mage_1 = new Mage("Paladino", "Alleanza");
        mage_2 = new Mage("Shamano", "Orda");
    }

    @Override
    public void onSpellCast(Spell spell) {
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
                break;
            case SPELL_LOSE:
                mage_1.obtainDamage(defense.getPower());
                break;
        }

        //mage_2.obtainDamage(attack.getPower());


        System.out.println(mage_1.getName()+":"+mage_1.getLifePoints()+" attack with "+attack.getType());
        System.out.println(mage_2.getName()+":"+mage_2.getLifePoints()+" defense with "+defense.getType());
    }

}
