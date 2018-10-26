package com.okeytime.dynamicsoload;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;


/**
 * 1、本来想着是否能直接复制源文件对应CPU的so库到apk安装后的so路径，但是没有去权限操作。
 * 所以只能手动指定so的路径。
 * 2、部分第三方SDK内部对so的加载进行了额外的操作，并且代码被加密过，这是这种思路就没办法
 * <p>
 * 源文件路径:FileUtils.getHostPath() + "/honganresource/" + zipName;
 * 解压路径:FileUtils.getHostPath() + "/honganresource/";
 * so库复制路径:this.getDir("so_libs", Activity.MODE_PRIVATE);
 */
public class MainActivity extends AppCompatActivity {
    private TextView logString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logString = (TextView) findViewById(R.id.log);

        /**
         * 默认解压地址:/data/data/app_so_libs/
         */
    }

    public void loadSo(View view) {
        //由于是例子，实际如解压缩等耗时间操作需在子线程中
        initJniDir();
    }

    /**
     * 初始化jni的目录结构
     */
    private void initJniDir() {
        File dir = this.getDir("so_libs", Activity.MODE_PRIVATE);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory() && file.length() > 0) {
                logString.setText("已经初始化过了...");
                Log.e("test", "已经初始化过了...");
                loadSo();
                return;
            }
        }

        File armeabi = new File(dir.getAbsolutePath() + File.separator + "armeabi");
        File armeabi_v7a = new File(dir.getAbsolutePath() + File.separator + "armeabi-v7a");
        File x86 = new File(dir.getAbsolutePath() + File.separator + "x86");
        File x86_64 = new File(dir.getAbsolutePath() + File.separator + "x86_64");

        if (readZipFile("jniLibs.zip")) {
            logString.setText("初始化完成...");
            Log.e("test", "初始化完成...");
            loadSo();
        } else {
            logString.setText("动态加载so库失败...");
            Log.e("test", "动态加载so库失败...");
        }
    }

    /**
     * 手动加载so库之前需要保证：有对应的jar包。在第三方初始化之前加载so
     * <否则会出现类没有找到的错误>
     */
    public void loadSo() {
        Log.e("test", "开始手动加载so库");
        //FileUtils.loadSoLib(this, "libeasemob_jni.so");
    }

    /**
     * 读取压缩包中的文件，解压到指定目录
     *
     * @throws Exception
     */
    public boolean readZipFile(String zipName) {
        boolean copyIsFinish = false;
        try {
            //源压缩包、文件
            String zipPath = FileUtils.getHostPath() + "/honganresource/" + zipName;
            //解压路径
            String path = FileUtils.getHostPath() + "/honganresource/";
            //so库复制路径
            File dir = this.getDir("so_libs", Activity.MODE_PRIVATE);
            //解压网络下载的压缩包到指定目录
            FileUtils.unZipFiles(zipPath, path);
            //从解压目录提取需要的so到app安装目录下
            FileUtils.copySoLib(new File(FileUtils.getHostPath() + "/honganresource/jniLibs/"), dir.getAbsolutePath() + "/");
            copyIsFinish = true;
        } catch (IOException e) {
            e.printStackTrace();
            logString.setText("[readZipFile] IOException " + e.toString());
        }
        return copyIsFinish;
    }
}
