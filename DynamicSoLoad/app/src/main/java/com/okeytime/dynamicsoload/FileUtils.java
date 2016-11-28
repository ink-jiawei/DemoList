package com.okeytime.dynamicsoload;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileUtils {
    /**
     * 检查设备是否存在SDCard的工具方法
     */
    private static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取根目录
     *
     * @return
     */
    public static String getHostPath() {
        String url;
        if (hasSdcard()) {
            url = Environment.getExternalStorageDirectory() + "";
        } else {
            url = Environment.getDataDirectory() + "";

        }
        return url;
    }

    /**
     * 压缩文件-由于out要在递归调用外,所以封装一个方法用来
     * 调用ZipFiles(ZipOutputStream out,String path,File... srcFiles)
     *
     * @param zip
     * @param path
     * @param srcFiles
     */
    public static void ZipFiles(File zip, String path, File... srcFiles) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
        ZipFiles(out, path, srcFiles);
        out.close();
        System.out.println("*****************压缩完毕*******************");
    }

    /**
     * 压缩文件-File
     *
     * @param srcFiles 被压缩源文件
     */
    public static void ZipFiles(ZipOutputStream out, String path, File... srcFiles) {
        path = path.replaceAll("\\*", "/");
        if (!path.endsWith("/")) {
            path += "/";
        }
        byte[] buf = new byte[1024];
        try {
            for (int i = 0; i < srcFiles.length; i++) {
                if (srcFiles[i].isDirectory()) {
                    File[] files = srcFiles[i].listFiles();
                    String srcPath = srcFiles[i].getName();
                    srcPath = srcPath.replaceAll("\\*", "/");
                    if (!srcPath.endsWith("/")) {
                        srcPath += "/";
                    }
                    out.putNextEntry(new ZipEntry(path + srcPath));
                    ZipFiles(out, path + srcPath, files);
                } else {
                    FileInputStream in = new FileInputStream(srcFiles[i]);
                    System.out.println(path + srcFiles[i].getName());
                    out.putNextEntry(new ZipEntry(path + srcFiles[i].getName()));
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    out.closeEntry();
                    in.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 解压到指定目录
     *
     * @param zipPath
     * @param descDir
     */
    public static void unZipFiles(String zipPath, String descDir) throws IOException {
        unZipFiles(new File(zipPath), descDir);
    }

    /**
     * 解压文件到指定目录
     *
     * @param zipFile
     * @param descDir
     */
    @SuppressWarnings("rawtypes")
    public static void unZipFiles(File zipFile, String descDir) throws IOException {
        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile);
        for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
            Log.e("test", "压缩包目录：" + outPath);
            //判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }

            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
    }

    /**
     * 将一个SO库复制到指定路径，会先检查改SO库是否与当前CPU兼容
     *
     * @param sourceDir SO库所在目录
     * @param destDir   目标根目录
     */
    public static void copySoLib(File sourceDir, String destDir) {
        File destFile = new File(destDir);

        if (!sourceDir.exists()) {
            return;
        }

        if (!destFile.exists()) {
            destFile.mkdirs();
        }
        Log.e("test", "[copySo] 开始处理so文件");

        //判断支持的CPU类型
        if (Build.VERSION.SDK_INT >= 21) {
            String[] abis = Build.SUPPORTED_ABIS;
            if (abis != null) {
                listFiles(sourceDir, destDir, abis);
            } else {
                Log.e("test", "[copySo] get abis == null");
            }
        } else {
            Log.e("test", "[copySo] supported api:" + Build.CPU_ABI + " " + Build.CPU_ABI2);
            String[] abis = {Build.CPU_ABI, Build.CPU_ABI2};
            listFiles(sourceDir, destDir, abis);
        }
    }

    /**
     * 递归遍历文件
     */
    public static void listFiles(File sourceDir, String destDir, String[] abis) {
        for (File file : sourceDir.listFiles()) {
            for (String abi : abis) {
                //Log.e("test", "listFiles:" + file.getAbsolutePath());

                if (file.getName().equals(abi)) {
                    copyFolder(file.getAbsolutePath(), destDir + file.getName());
                    //api21 64位系统的目录可能有些不同
                    //copyFile(sourceFile.getAbsolutePath(), destDir + File.separator +  name);
                }
//
//                if (file.isDirectory()) {
//                    listFiles(file, destDir, abis);
//                }
            }
        }
    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        Log.e("test", "目标文件夹：" + newPath);
        boolean isok = true;
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                Log.e("test", "源文件：" + temp.getAbsolutePath());
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    Log.e("test", "子文件夹:" + newPath + "/" + file[i]);
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            isok = false;
        }
        return isok;
    }

    /**
     * 手动加载指定文件名的so库
     */
    public static void loadSoLib(Context context, String soName) {
        File dir = context.getDir("so_libs", Activity.MODE_PRIVATE);

        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            File arm = files[0];
            if (arm != null && arm.isDirectory()) {
                for (File so : arm.listFiles()) {
                    if (so.exists() && so.isFile()) {
                        if (so.getName().equals(soName)) {
                            try {
                                Log.e("test", "手动加载:" + so.getAbsolutePath());
                                System.load(so.getAbsolutePath());
                            } catch (Exception e) {
                                Log.e("test", "Exception:" + e.toString());
                            }
                        }
                    }
                }
            } else {
                Log.e("test", "手动加载so失败，该目录下没有so文件");
            }
        }
    }
}