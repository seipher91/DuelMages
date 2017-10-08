package barile.vittorio.engine;

import lombok.Builder;
import lombok.Getter;

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

    public Spell(String name, String type, int power) {
        this.name = name;
        this.type = type;
        this.power = power;
    }

    public Spell(String name, String type) {
        this(name, type, 0);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    private static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

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
