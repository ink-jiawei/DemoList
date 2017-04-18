package com.inkhjw.myarchitecture.observable;

import android.util.Log;

/**
 * @author hjw
 */

public class DataObservableImpl implements IObservable {
    @Override
    public void changeText(String text) {
        Log.e("debug", text);
    }

    @Override
    public void changeImage(String bitmap) {
        Log.e("debug", bitmap);
    }
}
