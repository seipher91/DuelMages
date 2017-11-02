package barile.vittorio.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Algebra {

    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(java.lang.Math.random() * range) + min;
    }
}
