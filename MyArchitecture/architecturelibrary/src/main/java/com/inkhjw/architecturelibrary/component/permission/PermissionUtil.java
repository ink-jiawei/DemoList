package com.inkhjw.architecturelibrary.component.permission;

import android.Manifest;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Subscriber;

/**
 */

public class PermissionUtil {
    public static final String TAG = "Permission";

    public interface RequestPermission {
        void onRequestPermissionSuccess();
    }


    /**
     * 请求摄像头权限
     */
    public static void launchCamera(final RequestPermission requestPermission, RxPermissions rxPermissions) {
        //先确保是否已经申请过摄像头，和写入外部存储的权限
        boolean isPermissionsGranted =
                rxPermissions
                        .isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                        rxPermissions
                                .isGranted(Manifest.permission.CAMERA);

        if (isPermissionsGranted) {//已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过，则申请
            rxPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE
                            , Manifest.permission.CAMERA)
                    .subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Boolean granted) {
                            if (granted) {
                                requestPermission.onRequestPermissionSuccess();
                            } else {
                            }
                        }
                    });
        }
    }


    /**
     * 请求外部存储的权限
     */
    public static void externalStorage(final RequestPermission requestPermission, RxPermissions rxPermissions) {
        //先确保是否已经申请过摄像头，和写入外部存储的权限
        boolean isPermissionsGranted =
                rxPermissions
                        .isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (isPermissionsGranted) {//已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过，则申请
            rxPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Boolean granted) {
                            if (granted) {
                                requestPermission.onRequestPermissionSuccess();
                            } else {
                            }
                        }
                    });
        }
    }


    /**
     * 请求发送短信权限
     */
    public static void sendSms(final RequestPermission requestPermission, RxPermissions rxPermissions) {
//先确保是否已经申请过权限
        boolean isPermissionsGranted =
                rxPermissions
                        .isGranted(Manifest.permission.SEND_SMS);

        if (isPermissionsGranted) {//已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过，则申请
            rxPermissions
                    .request(Manifest.permission.SEND_SMS)
                    .subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Boolean granted) {
                            if (granted) {
                                requestPermission.onRequestPermissionSuccess();
                            } else {
                            }
                        }
                    });
        }
    }


    /**
     * 请求打电话权限
     */
    public static void callPhone(final RequestPermission requestPermission, RxPermissions rxPermissions) {
//先确保是否已经申请过权限
        boolean isPermissionsGranted =
                rxPermissions
                        .isGranted(Manifest.permission.CALL_PHONE);

        if (isPermissionsGranted) {//已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过，则申请
            rxPermissions
                    .request(Manifest.permission.CALL_PHONE)
                    .subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Boolean granted) {
                            if (granted) {
                                requestPermission.onRequestPermissionSuccess();
                            } else {
                            }
                        }
                    });
        }
    }


    /**
     * 请求获取手机状态的权限
     */
    public static void readPhonestate(final RequestPermission requestPermission, RxPermissions rxPermissions) {
//先确保是否已经申请过权限
        boolean isPermissionsGranted =
                rxPermissions
                        .isGranted(Manifest.permission.READ_PHONE_STATE);

        if (isPermissionsGranted) {//已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过，则申请
            rxPermissions
                    .request(Manifest.permission.READ_PHONE_STATE)
                    .subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Boolean granted) {
                            if (granted) {
                                requestPermission.onRequestPermissionSuccess();
                            } else {
                            }
                        }
                    });
        }
    }

}

