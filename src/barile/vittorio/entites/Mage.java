package barile.vittorio.entites;

import barile.vittorio.engine.Spell;
import barile.vittorio.interfaces.IntellectualAbilities;
import barile.vittorio.interfaces.Vitality;
import barile.vittorio.ui.interfaces.OnVitalityEventListener;
import com.sun.istack.internal.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Mage implements Vitality, IntellectualAbilities {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String accademic_class;

    private int life_points;
    private List<Spell> abilities;
    private OnVitalityEventListener listener;

    public Mage(String name, String accademic_class, @Nullable OnVitalityEventListener listener) {
        this.name = name;
        this.accademic_class = accademic_class;

        this.abilities = new ArrayList<>();
        this.life_points = 100;
        this.listener = listener;
    }

    @Override
    public List<String> getAbilitiesType() {
        List<String> types = new ArrayList<>();

        for (Spell spell : getAbilities()) {
            if (types.contains(spell.getType())) continue;
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
                .power(spell.getPower())
                .build();
    }

    @Override
    public int getLifePoints() {
        return this.life_points;
    }

    @Override
    public void obtainDamage(int damage) {
        this.life_points -= damage;
        if (this.life_points <= 0){
            this.life_points = 0;

            if(this.listener!=null)
                this.listener.onDeath(this.name);
        }
    }

    @Override
    public void obtainHeal(int heal) {
        this.life_points += heal;
    }

}
