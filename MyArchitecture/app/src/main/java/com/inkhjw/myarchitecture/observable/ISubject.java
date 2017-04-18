package com.inkhjw.myarchitecture.observable;

/**
 * @author hjw
 *         目标接口，被观察者（发生改变的一方）
 */

public interface ISubject {

    void subscribe(IObservable observable);

    void unSubscribe(IObservable observable);

    void changeText(String text);

    void changeImage(String bitmap);
}
