package com.inkhjw.bitmapdisplaydemo.ImageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author hjw
 * @deprecated
 */
public class ImageFileCache {
    private final String CACHE_Dir = "/sdcard/inkCache";
    /**
     * 文件缓存的后缀名
     */
    private final String CACHE_LAST_S = ".jpg";

    /**
     * 从文件缓存中获取图片
     *
     * @param url
     * @return
     */
    public Bitmap getImageFromFile(String url) {
        Bitmap bitmap = null;

        String path = CACHE_Dir + "/" + url.hashCode() + CACHE_LAST_S;
        File file = new File(path);
        if (file != null) {
            if (file.exists()) {
                Log.e("test", "文件缓存:" + (file.length() / 1024) + "kb");
                bitmap = BitmapFactory.decodeFile(path);
                updateCacheTime(file);
            } else {
                file.delete();
            }
        }
        return bitmap;
    }

    /**
     * 添加bitmap到文件缓存
     *
     * @param url
     * @param bitmap
     */
    public void addBitmapCache(String url, Bitmap bitmap) {
        if (SdCardFreeSpace() < 20) {
            clearCache();
            return;
        }
        if (bitmap == null) {
            return;
        }

        //目录是否存在
        File dirPath = new File(CACHE_Dir);
        if (!dirPath.exists()) {
            dirPath.mkdir();
        }

        String path = CACHE_Dir + "/" + url.hashCode() + CACHE_LAST_S;
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
                OutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新最新使用到的缓存图片的时间
     */

    public void updateCacheTime(File file) {
        file.setLastModified(System.currentTimeMillis());
    }

    /**
     * 计算SD卡上的剩余空间
     */
    private int SdCardFreeSpace() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize());
        return (int) sdFreeMB;
    }

    /**
     * 清空文件缓存
     */
    public void clearCache() {
        File dirCache = new File(CACHE_Dir);
        if (!dirCache.exists()) {
            return;
        }

        for (File file : dirCache.listFiles()) {
            file.delete();
        }
    }
}
