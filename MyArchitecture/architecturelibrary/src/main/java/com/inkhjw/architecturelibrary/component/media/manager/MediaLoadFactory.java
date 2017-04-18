package com.inkhjw.architecturelibrary.component.media.manager;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import java.io.File;

public class MediaLoadFactory {
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;
    public int index = 0;
    private final static String[] IMAGE_PROJECTION = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media._ID
    };
    private static final String SELECTION = MediaStore.Images.Media.MIME_TYPE
            + "=? or " + MediaStore.Images.Media.MIME_TYPE
            + "=?" + " or " + MediaStore.Images.Media.MIME_TYPE
            + "=?";
    private static final String[] SELECTION_ARGS = {"image/jpeg", "image/png", "image/gif"};
    private static final String SORT = IMAGE_PROJECTION[2] + " DESC";


    private final static String[] VIDEO_PROJECTION = {
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DURATION,
    };

    private int type = TYPE_IMAGE;
    private FragmentActivity activity;


    public MediaLoadFactory(FragmentActivity activity, int type) {
        this.activity = activity;
        this.type = type;
    }

    public void loadAllImage() {
        activity.getSupportLoaderManager().initLoader(type, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                CursorLoader cursorLoader = null;
                if (id == TYPE_IMAGE) {
                    cursorLoader = new CursorLoader(
                            activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            IMAGE_PROJECTION, SELECTION, SELECTION_ARGS, SORT);

                } else if (id == TYPE_VIDEO) {
                    cursorLoader = new CursorLoader(
                            activity, MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            VIDEO_PROJECTION, null, null, VIDEO_PROJECTION[2] + " DESC");
                }
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                try {
                    if (data != null) {
                        int count = data.getCount();
                        if (count > 0) {
                            data.moveToFirst();
                            do {
                                String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                                String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                                // 如原图路径不存在或者路径存在但文件不存在,就结束当前循环
                                if (TextUtils.isEmpty(path) || !new File(path).exists()) {
                                    continue;
                                }
                                long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                                int duration = (type == TYPE_VIDEO ? data.getInt(data.getColumnIndexOrThrow(VIDEO_PROJECTION[4])) : 0);


                            } while (data.moveToNext());

                            //数据加载完成

                            data.close();
                        } else {
                            //没有一张图片
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
            }
        });
    }
}