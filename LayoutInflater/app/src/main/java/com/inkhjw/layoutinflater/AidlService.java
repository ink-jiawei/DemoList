package com.inkhjw.layoutinflater;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.InputQueue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
            Window window = new Window() {
                @Override
                public void takeSurface(SurfaceHolder.Callback2 callback2) {

                }

                @Override
                public void takeInputQueue(InputQueue.Callback callback) {

                }

                @Override
                public boolean isFloating() {
                    return false;
                }

                @Override
                public void setContentView(int i) {

                }

                @Override
                public void setContentView(View view) {

                }

                @Override
                public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {

                }

                @Override
                public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {

                }

                @Nullable
                @Override
                public View getCurrentFocus() {
                    return null;
                }

                @NonNull
                @Override
                public LayoutInflater getLayoutInflater() {
                    return null;
                }

                @Override
                public void setTitle(CharSequence charSequence) {

                }

                @Override
                public void setTitleColor(int i) {

                }

                @Override
                public void openPanel(int i, KeyEvent keyEvent) {

                }

                @Override
                public void closePanel(int i) {

                }

                @Override
                public void togglePanel(int i, KeyEvent keyEvent) {

                }

                @Override
                public void invalidatePanelMenu(int i) {

                }

                @Override
                public boolean performPanelShortcut(int i, int i1, KeyEvent keyEvent, int i2) {
                    return false;
                }

                @Override
                public boolean performPanelIdentifierAction(int i, int i1, int i2) {
                    return false;
                }

                @Override
                public void closeAllPanels() {

                }

                @Override
                public boolean performContextMenuIdentifierAction(int i, int i1) {
                    return false;
                }

                @Override
                public void onConfigurationChanged(Configuration configuration) {

                }

                @Override
                public void setBackgroundDrawable(Drawable drawable) {

                }

                @Override
                public void setFeatureDrawableResource(int i, int i1) {

                }

                @Override
                public void setFeatureDrawableUri(int i, Uri uri) {

                }

                @Override
                public void setFeatureDrawable(int i, Drawable drawable) {

                }

                @Override
                public void setFeatureDrawableAlpha(int i, int i1) {

                }

                @Override
                public void setFeatureInt(int i, int i1) {

                }

                @Override
                public void takeKeyEvents(boolean b) {

                }

                @Override
                public boolean superDispatchKeyEvent(KeyEvent keyEvent) {
                    return false;
                }

                @Override
                public boolean superDispatchKeyShortcutEvent(KeyEvent keyEvent) {
                    return false;
                }

                @Override
                public boolean superDispatchTouchEvent(MotionEvent motionEvent) {
                    return false;
                }

                @Override
                public boolean superDispatchTrackballEvent(MotionEvent motionEvent) {
                    return false;
                }

                @Override
                public boolean superDispatchGenericMotionEvent(MotionEvent motionEvent) {
                    return false;
                }

                @Override
                public View getDecorView() {
                    return null;
                }

                @Override
                public View peekDecorView() {
                    return null;
                }

                @Override
                public Bundle saveHierarchyState() {
                    return null;
                }

                @Override
                public void restoreHierarchyState(Bundle bundle) {

                }

                @Override
                protected void onActive() {

                }

                @Override
                public void setChildDrawable(int i, Drawable drawable) {

                }

                @Override
                public void setChildInt(int i, int i1) {

                }

                @Override
                public boolean isShortcutKey(int i, KeyEvent keyEvent) {
                    return false;
                }

                @Override
                public void setVolumeControlStream(int i) {

                }

                @Override
                public int getVolumeControlStream() {
                    return 0;
                }

                @Override
                public int getStatusBarColor() {
                    return 0;
                }

                @Override
                public void setStatusBarColor(int i) {

                }

                @Override
                public int getNavigationBarColor() {
                    return 0;
                }

                @Override
                public void setNavigationBarColor(int i) {

                }

                @Override
                public void setDecorCaptionShade(int i) {

                }

                @Override
                public void setResizingCaptionDrawable(Drawable drawable) {

                }
            }

            Log.e("test", "调起AIDL服务:" + code);
            Toast.makeText(getApplicationContext(), "调起AIDL服务:" + code, Toast.LENGTH_LONG).show();
        }
    }
}
