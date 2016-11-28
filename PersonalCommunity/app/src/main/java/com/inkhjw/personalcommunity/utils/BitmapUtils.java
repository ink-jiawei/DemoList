package com.inkhjw.personalcommunity.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BitmapUtils {

    /***
     * 图片的缩放方法，矩阵缩放
     *
     * @param bgimage   ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 根据后缀名判断是否是图片文件
     *
     * @param fileName
     * @return 是否是图片结果true or false
     */
    public static boolean isImage(String fileName) {
        String type = FileUtils.getFileType(fileName);
        if (type != null && (type.equals("jpeg") || type.equals("png") || type
                .equals("jpg"))) {
            return true;
        }
        return false;
    }
}
