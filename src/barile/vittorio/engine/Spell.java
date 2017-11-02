package barile.vittorio.engine;

import barile.vittorio.utils.Algebra;
import lombok.Builder;
import lombok.Getter;

/**
 * La Magia e' la materia offensiva/difensiva tra i {@link barile.vittorio.entites.Mage}
 * @author Vittorio
 */
@Builder
public class Spell {
    public static final String FIRE_TYPE = "Fire";
    public static final String FROST_TYPE = "Frost";
    public static final String ARCANE_TYPE = "Arcane";

    @Getter
    private String name;

    @Getter
    private String type;

    @Getter
    private int power;

    /**
     * Definisce una Magia che e' l'unita' di interazione tra i {@link barile.vittorio.entites.Mage}
     * @param name nome della Magia
     * @param type tipologia di magia: {@value #FIRE_TYPE}, {@value #FROST_TYPE}, {@value #ARCANE_TYPE}
     * @param power potenza di danno della magia
     */
    public Spell(String name, String type, int power) {
        this.name = name;
        this.type = type;
        this.power = power;
    }

    public Spell(String name, String type) {
        this(name, type, 0);
    }

    /**
     * Permette di ottenere a caso una tipologia di magia
     * @return tipologia di magia: {@value #FIRE_TYPE}, {@value #FROST_TYPE}, {@value #ARCANE_TYPE}
     */
    public static String getRandomType() {
        int choice = Algebra.randomWithRange(0, 2);

        switch (choice) {
            default:
            case 0: return FIRE_TYPE;
            case 1: return FROST_TYPE;
            case 2: return ARCANE_TYPE;
        }
    }


    public static final int SPELL_DRAW = -1;
    public static final int SPELL_WIN = 0;
    public static final int SPELL_LOSE = 1;

    /**
     * Compara l'esito offensivo tra {@link Spell}
     * @param spell Magia che si vuole contrastare
     * @return esito di tipo: {@value #SPELL_DRAW}, {@value #SPELL_WIN}, {@value #SPELL_LOSE}
     */
    public int tryOffense(Spell spell) {
        if(spell.getType().equals(type)) return SPELL_DRAW;

        switch (type) {
            case FIRE_TYPE:
                return spell.getType().equals(ARCANE_TYPE) ? SPELL_WIN : SPELL_LOSE;
            case FROST_TYPE:
                return spell.getType().equals(FIRE_TYPE) ? SPELL_WIN : SPELL_LOSE;
            case ARCANE_TYPE:
                return spell.getType().equals(FROST_TYPE) ? SPELL_WIN : SPELL_LOSE;
        }

        return SPELL_DRAW;
    }

}
