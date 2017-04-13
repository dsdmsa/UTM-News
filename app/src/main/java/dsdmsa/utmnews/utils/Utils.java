package dsdmsa.utmnews.utils;


import java.util.Random;

public class Utils {
    private static Random random = new Random();

    private Utils() {
    }

    public static int getRandomNumber(int range){
        return random.nextInt(range);
    }

}
