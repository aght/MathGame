package android.bcit.ca.mathgame.utility;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

import static android.bcit.ca.mathgame.utility.RandomNumberGenerator.randomInt;

public class ColorGenerator {
    public static int getMaterialColor(Context context,  String typeColor) {
        int returnColor = Color.BLACK;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + typeColor, "array", context.getApplicationContext().getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = randomInt(0, colors.length() - 1);
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        }

        return returnColor;
    }
}
