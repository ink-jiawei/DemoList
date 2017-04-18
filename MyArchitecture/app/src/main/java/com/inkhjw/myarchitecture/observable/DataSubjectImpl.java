package com.inkhjw.myarchitecture.observable;

import java.util.ArrayList;

/**
 * @author hjw
 */

public class DataSubjectImpl implements ISubject {
    private ArrayList<IObservable> observables = new ArrayList<>();

    @Override
    public void subscribe(IObservable observable) {
        if (!observables.contains(observable)) {
            observables.add(observable);
        }
    }

    @Override
    public void unSubscribe(IObservable observable) {
        if (observables.contains(observable)) {
            observables.remove(observable);
        }
    }

    @Override
    public void changeText(String text) {
        for (IObservable observable : observables) {
            observable.changeText(text);
        }
    }

    @Override
    public void changeImage(String bitmap) {
        for (IObservable observable : observables) {
            observable.changeImage(bitmap);
        }
    }
}
