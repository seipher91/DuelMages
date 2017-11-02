package barile.vittorio.entites;

import barile.vittorio.engine.Spell;
import barile.vittorio.interfaces.IntellectualAbilities;
import barile.vittorio.interfaces.Vitality;
import barile.vittorio.ui.interfaces.OnVitalityEventListener;
import barile.vittorio.utils.Algebra;
import com.sun.istack.internal.Nullable;
import lombok.Getter;
import lombok.Setter;


/**
 * Il Mago e' l'entita' principale con cui ci immedesimiamo nel gioco
 * @author Vittorio
 */
public class Mage implements Vitality, IntellectualAbilities {
    public static final int MAX_LIFE = 100;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String accademic_class;

    private int life_points;
    private OnVitalityEventListener listener;

    /**
     * Definisce un Mago
     * @param name nome del Mago
     * @param accademic_class Classe accademica
     * @param listener delega per le mutazioni della vita
     */
    public Mage(String name, String accademic_class, @Nullable OnVitalityEventListener listener) {
        this.name = name;
        this.accademic_class = accademic_class;

        this.life_points = MAX_LIFE;
        this.listener = listener;
    }

    /**
     * Materializza una magia offensiva
     * @param spell Magia grezza, priva di potenziale
     * @return Magia caricata
     */
    @Override
    public Spell execAttack(Spell spell) {
        return Spell.builder()
                .name(spell.getName())
                .type(spell.getType())
                .power(Algebra.randomWithRange(15, 25))
                .build();
    }

    /**
     * Ritorna i punti vita
     * @return unita' di punti vita
     */
    @Override
    public int getLifePoints() {
        return this.life_points;
    }

    /**
     * Applica un danno alla vitalita' del Mago
     * @param damage unita' di punti vita da sottrarre
     */
    @Override
    public void obtainDamage(int damage) {
        this.life_points -= damage;
        if (this.life_points <= 0){
            this.life_points = 0;

            if(this.listener!=null)
                this.listener.onDeath(this.name);
        }
    }

    /**
     * Applica una rigenerazione alla vitalita' del Mago
     * @param heal unita' di punti vita da aggiungere
     */
    @Override
    public void obtainHeal(int heal) {
        this.life_points += heal;
        if (this.life_points > MAX_LIFE)
            this.life_points = MAX_LIFE;
    }

}
