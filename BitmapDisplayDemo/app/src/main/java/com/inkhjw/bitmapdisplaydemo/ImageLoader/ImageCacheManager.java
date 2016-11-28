package com.inkhjw.bitmapdisplaydemo.ImageLoader;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

/**
 * @author hjw
 * @deprecated 实现volley的图片缓存接口，自定义实现图片的三级缓存
 */
public class ImageCacheManager implements ImageLoader.ImageCache {
    private static ImageCacheManager imageCacheManager;//唯一单例
    private ImageMemoryCache memoryCache;//内存缓存
    private ImageFileCache fileCache;//文件缓存

    private RequestQueue queue;
    private ImageLoader imageLoader;

    private boolean isMemoryCache = false;
    private boolean isFileCache = false;

    /**
     * ImageCacheManager单例模式
     *
     * @return
     */
    public static ImageCacheManager getInstance() {
        if (imageCacheManager == null) {
            synchronized (ImageCacheManager.class) {
                if (imageCacheManager == null) {
                    imageCacheManager = new ImageCacheManager();
                }
            }
        }
        return imageCacheManager;
    }

    private ImageCacheManager() {
        cacheInit();
    }

    /**
     * 初始化
     */
    private void cacheInit() {
        memoryCache = new ImageMemoryCache();
        fileCache = new ImageFileCache();
    }

    /**
     * 设置请求队列，初始化imageLoader
     *
     * @param queue
     */
    public void addRequestQueue(RequestQueue queue) {
        this.queue = queue;
        imageLoader = new ImageLoader(queue, this);
    }

    /**
     * 加载图片-需先调用setRequestQueue()
     *
     * @param stringUrl
     * @param imageView
     * @return Bitmap
     */
    public Bitmap loaderImage(String stringUrl, ImageView imageView, int defaultImageResId, int errorImageResId, int maxWidth, int maxHeight) {
        if (queue == null) {
            throw new IllegalArgumentException("RequestQueue initException,请确认加载图片前是否调用setRequestQueue()");
        }
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.height = maxHeight;
        params.width = maxWidth;
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ImageLoader.ImageListener listener = imageLoader.getImageListener(imageView, defaultImageResId, errorImageResId);
        ImageLoader.ImageContainer container = imageLoader.get(stringUrl, listener, maxWidth, maxHeight);
        return container.getBitmap();
    }

    /**
     * 加载图片-需先调用setRequestQueue()
     *
     * @param stringUrl
     * @param imageView
     * @return Bitmap
     */
    public Bitmap loaderImage(String stringUrl, ImageView imageView, int defaultImageResId, int errorImageResId) {
        if (queue == null) {
            throw new IllegalArgumentException("RequestQueue initException,请确认加载图片前是否调用setRequestQueue()");
        }
        ImageLoader.ImageListener listener = imageLoader.getImageListener(imageView, defaultImageResId, errorImageResId);
        ImageLoader.ImageContainer container = imageLoader.get(stringUrl, listener);
        return container.getBitmap();
    }

    /**
     * 加载缓存
     *
     * @param stringUrl
     * @return
     */
    private Bitmap loaderImage(String stringUrl) {

        //一级内存缓存
        Bitmap memoryBitmap = memoryCache.getBitmapFromMemory(stringUrl);
        if (memoryBitmap != null) {
            Log.e("test", "内存缓存:" + (memoryBitmap.getByteCount() / 1024) + "kb");
            return memoryBitmap;
        }
        //三级文件缓存
        Bitmap fileBitmap = fileCache.getImageFromFile(stringUrl);
        if (fileBitmap != null) {
            memoryCache.addBitmapCache(stringUrl, fileBitmap);
            return fileBitmap;
        }
        Log.e("test", "没有缓存");
        return null;
    }

    @Override
    public Bitmap getBitmap(String s) {
        return loaderImage(s);
    }

    /**
     * 添加缓存
     *
     * @param s
     * @param bitmap
     */
    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        if (isMemoryCache) {
            memoryCache.addBitmapCache(s, bitmap);
        }
        if (isFileCache) {
            fileCache.addBitmapCache(s, bitmap);
        }
    }

    /**
     * 清除缓存<内存+文件>
     */
    public void clearCache() {
        memoryCache.clearCache();
        fileCache.clearCache();
    }

    /**
     * 是否开启文件缓存
     *
     * @return
     */
    public boolean isFileCache() {
        return isFileCache;
    }

    /**
     * 是否开启文件缓存
     *
     * @return
     */
    public void setFileCache(boolean fileCache) {
        isFileCache = fileCache;
    }

    /**
     * 是否开启内存缓存
     *
     * @return
     */
    public boolean isMemoryCache() {
        return isMemoryCache;
    }

    /**
     * 是否开启内存缓存
     *
     * @return
     */
    public void setMemoryCache(boolean memoryCache) {
        isMemoryCache = memoryCache;
    }
}
