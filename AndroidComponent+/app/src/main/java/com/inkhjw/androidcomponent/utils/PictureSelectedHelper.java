package com.inkhjw.androidcomponent.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.inkhjw.androidcomponent.component.toast.ToastView;

import java.io.File;

public final class PictureSelectedHelper {
    final static int TO_CAMERA = 0x0010;//相机
    final static int TO_GALLERY = 0x0011;//相册
    final static int TO_CUTTING = 0x0012;//裁剪图片

    private Activity context;

    private PictureSelectedHelper() {
    }

    public PictureSelectedHelper(Activity context) {
        this.context = context;
    }

    /**
     * 去相机
     */
    public void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                .fromFile(new File(FileUtils.getCachePath(), FileUtils.TAKE_UP_NAME)));
        context.startActivityForResult(intent, TO_CAMERA);
    }

    /**
     * 去相册
     */
    public void toGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        context.startActivityForResult(intent, TO_GALLERY);
    }

    /**
     * 去裁剪图片
     *
     * @param uri
     */
    public void toCuttingPicture(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        context.startActivityForResult(intent, TO_CUTTING);
    }

    public Bitmap reposePictureSelected(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;

        switch (requestCode) {
            case TO_GALLERY:
                if (data == null || data.getData() == null) {
                    return null;
                }

                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        Uri uri = data.getData();

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        //从系统表中查询指定Uri对应的照片
                        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
                        if (cursor == null) {
                            return null;
                        }

                        if (!cursor.moveToFirst()) {
                            return null;
                        }

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);  //获取照片路径
                        cursor.close();
                        if (!TextUtils.isEmpty(picturePath)) {
                            toCuttingPicture(uri);
                        } else {
                            ToastView.showToast(context, "获取图像资源失败");
                        }
                    }
                }

                break;
            case TO_CAMERA:
                if (resultCode == -1) {

                    File tempFile = new File(FileUtils.getCachePath(),
                            FileUtils.TAKE_UP_NAME);
                    if (tempFile.exists()) {
                        toCuttingPicture(Uri.fromFile(tempFile));
                    } else {
                        ToastView.showToast(context, "获取图像资源失败");
                    }
                }

                break;
            case TO_CUTTING:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            bitmap = extras.getParcelable("data");
                        }
                    }
                }
                break;
        }
        return bitmap;
    }
}
