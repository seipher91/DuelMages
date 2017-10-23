package barile.vittorio.engine;

import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author Vittorio
 *
 * Classe per la creazione di magie in base al tipo, nome e potenza.
 *
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
     * Costruttore di una magia
     * @param name nome del mago
     * @param type tipologia di magia: Fire, Frost, Arcane
     * @param power potenza ddi danno della magia, un intero compreso tra 10 - 25
     */
    public Spell(String name, String type, int power) {
        this.name = name;
        this.type = type;
        this.power = randomWithRange(10, 25);
    }

    public Spell(String name, String type) {
        this(name, type, 0);
    }

    private static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    /**
     * Metodo che permette di ottenere a caso una tipologia di magia
     * @return valore intero a caso compreso tra 0 e 2
     */
    public static String getRandomType() {
        int choice = randomWithRange(0, 2);

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

    public int tryOffense(Spell spell) {
        int result = SPELL_DRAW;

        if(spell.getType().equals(type)) return SPELL_DRAW;

        switch (type) {
            case FIRE_TYPE:
                return spell.getType().equals(ARCANE_TYPE) ? SPELL_WIN : SPELL_LOSE;
            case FROST_TYPE:
                return spell.getType().equals(FIRE_TYPE) ? SPELL_WIN : SPELL_LOSE;
            case ARCANE_TYPE:
                return spell.getType().equals(FROST_TYPE) ? SPELL_WIN : SPELL_LOSE;
        }

        return result;
    }

}
