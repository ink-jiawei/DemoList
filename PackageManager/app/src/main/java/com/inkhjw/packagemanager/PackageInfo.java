package com.inkhjw.packagemanager;

import android.graphics.drawable.Drawable;

/**
 * @author hjw
 */

public class PackageInfo {
    private String appLabel;
    private Drawable appIcon;
    private String appPackageName;

    public PackageInfo() {
    }

    public PackageInfo(String appLabel, Drawable appIcon, String appPackageName) {
        this.appLabel = appLabel;
        this.appIcon = appIcon;
        this.appPackageName = appPackageName;
    }

    public String getAppLabel() {
        return appLabel;
    }

    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }
}
