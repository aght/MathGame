package android.bcit.ca.mathgame.utility;

import java.util.Random;

public class RandomNumberGenerator {
    private static final Random random = new Random();

    public static int randomInt(int low, int high) {
        return random.nextInt((high + 1) - low) + low;
    }
}
