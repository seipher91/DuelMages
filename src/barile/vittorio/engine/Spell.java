package barile.vittorio.engine;

public class Spell {
    public static final String FIRE_TYPE = "Fire";
    public static final String FROST_TYPE = "Frost";
    public static final String ARCANE_TYPE = "Arcane";

    private String name;
    private String type;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String type;
        private int power;

        public Builder() {}

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder power(int power) {
            this.power = power;
            return this;
        }

        public Spell build() {
            return new Spell(name, type, power);
        }

    }


}
