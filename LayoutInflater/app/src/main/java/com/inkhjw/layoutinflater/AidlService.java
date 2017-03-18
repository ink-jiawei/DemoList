package com.inkhjw.layoutinflater;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class AidlService extends Service {
    AidlImpl aidl = new AidlImpl();

    public AidlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return aidl;
    }

    class AidlImpl extends IMyAidlInterface.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void aidlInit(int code) throws RemoteException {
            Log.e("test", "调起AIDL服务:" + code);
            Toast.makeText(getApplicationContext(), "调起AIDL服务:" + code, Toast.LENGTH_LONG).show();
        }
    }
}
