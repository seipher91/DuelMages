package barile.vittorio.interfaces;

import barile.vittorio.engine.Spell;

import java.util.List;

public interface IntellectualAbilities {
    public List<String> getAbilitiesType();
    public List<Spell> getAbilities();
    public Spell execAttack(Spell spell);
}
