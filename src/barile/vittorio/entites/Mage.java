package barile.vittorio.entites;

import barile.vittorio.engine.Spell;
import barile.vittorio.interfaces.IntellectualAbilities;
import barile.vittorio.interfaces.Vitality;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Mage implements Vitality, IntellectualAbilities {
    @Getter @Setter
    private String name;

    @Getter @Setter
    private String accademic_class;

    private int life_points;
    private List<Spell> abilities;

    public Mage(String name, String accademic_class) {
        this.name = name;
        this.accademic_class = accademic_class;

        this.abilities = new ArrayList<>();
        this.life_points = 100;
    }

    @Override
    public List<String> getAbilitiesType() {
        List<String> types = new ArrayList<>();

        for(Spell spell : getAbilities()) {
            if(types.contains(spell.getType())) continue;
            types.add(spell.getType());
        }

        return types;
    }

    @Override
    public List<Spell> getAbilities() {
        return abilities;
    }

    @Override
    public Spell execAttack(Spell spell) {
        return Spell.builder()
                .name(spell.getName())
                .type(spell.getType())
                .power(25)
                .build();
    }

    @Override
    public int getLifePoints() {
        return this.life_points;
    }

    @Override
    public void obtainDamage(int damage) {
        this.life_points -= damage;
    }

    @Override
    public void obtainHeal(int heal) {
        this.life_points += heal;
    }

}
