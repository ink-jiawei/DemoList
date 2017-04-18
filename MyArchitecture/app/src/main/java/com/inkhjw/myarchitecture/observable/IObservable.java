package com.inkhjw.myarchitecture.observable;

/**
 * @author hjw
 *         观察者（接受通知改变的一方）
 */

public interface IObservable {

    void changeText(String text);

    void changeImage(String bitmap);
}
