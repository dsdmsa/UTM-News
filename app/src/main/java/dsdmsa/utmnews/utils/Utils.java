package dsdmsa.utmnews.utils;


import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import java.util.Random;

public class Utils {
    private static Random random = new Random();

    private Utils() {
    }

    public static int getRandomNumber(int range){
        return random.nextInt(range);
    }

    private static int screenHeight = 0;

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }
        return screenHeight;
    }

}
