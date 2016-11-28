package com.inkhjw.bitmapdisplaydemo.ImageLoader;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * @author hjw
 * @deprecated
 */
public class ImageMemoryCache {
    /**
     * 内存缓存
     */
    private LruCache<String, Bitmap> lruCache;
    /**
     * 图片内存缓存空间内存大小
     */
    private int cacheSize;

    public ImageMemoryCache() {
        //获取系统最大运行内存
        int memorySize = (int) Runtime.getRuntime().maxMemory();
        cacheSize = memorySize / 1024 / 8;

        lruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                if (bitmap != null) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                } else {
                    return 0;
                }
            }
        };
    }

    /**
     * 从内存缓存中获取图片
     */
    public Bitmap getBitmapFromMemory(String url) {
        Bitmap bitmap;

        //加上同步锁,防止异步加载图片时对LruCache的读写冲突
        synchronized (lruCache) {
            bitmap = lruCache.get(url);
            if (bitmap != null) {
                // 如果找到的话，把元素移到LinkedHashMap的最前面，从而保证在LRU算法中是最后被删除
                lruCache.remove(url);
                lruCache.put(url, bitmap);
            }
        }
        return bitmap;
    }

    /**
     * 添加bitmap到内存缓存
     *
     * @param url
     * @param bitmap
     */
    public void addBitmapCache(String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (lruCache) {
                lruCache.put(url, bitmap);
            }
        }
    }

    /**
     * 清除内存缓存数据
     */
    public void clearCache() {
        lruCache.evictAll();
    }
}
