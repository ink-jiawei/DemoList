package com.hjw.testapplication;

import android.util.Log;

import java.util.Random;

/**
 * @author hjw
 */

public class SingleUtils {
    private static final long multiplier = 0x5deece66dL;

    public static boolean getSingle() {
        boolean flag = new Random().nextBoolean();
        Log.e("debug", "结果：" + flag);
        return flag;
    }

    public static boolean nextBoolean() {
        return next(1) != 0;
    }

    protected static synchronized int next(int bits) {
        long seed = (System.nanoTime() ^ multiplier) & ((1L << 48) - 1);
        seed = (seed * multiplier + 0xbL) & ((1L << 48) - 1);
        return (int) (seed >>> (48 - bits));
    }
}
