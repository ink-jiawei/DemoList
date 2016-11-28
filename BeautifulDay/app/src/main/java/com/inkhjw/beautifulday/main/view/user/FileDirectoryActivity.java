package com.inkhjw.beautifulday.main.view.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.inkhjw.beautifulday.R;
import com.inkhjw.beautifulday.adapter.FileDirectoryAdapter;
import com.inkhjw.beautifulday.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 文件目录管理
 */
public class FileDirectoryActivity extends BaseActivity {
    @Bind(R.id.file_path)
    TextView file_path;
    @Bind(R.id.file_listview)
    ListView file_listview;

    private String ROOT_PATH = "/";
    //存储文件名称
    private ArrayList<String> names = null;
    //存储文件路径
    private ArrayList<String> paths = null;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_directory);
    }

    @Override
    public void initView() {
        super.initView();
        initToolbar(false, "文件目录", 0);
    }

    @Override
    protected void initData() {
        super.initData();
        showFileDir(ROOT_PATH);
    }

    /**
     * 文件目录
     */
    public void showFileDir(String path) {
        file_path.setText(path);
        names = new ArrayList<>();
        paths = new ArrayList<>();
        File file = new File(path);
        File[] files = file.listFiles();
        //如果当前目录不是根目录
        if (!ROOT_PATH.equals(path)) {
            names.add("@1");
            paths.add(ROOT_PATH);

            names.add("@2");
            paths.add(file.getParent());
        }
        //添加所有文件
        for (File f : files) {
            names.add(f.getName());
            paths.add(f.getPath());
        }
        file_listview.setAdapter(new FileDirectoryAdapter(this, names, paths));
    }

    //对文件进行增删改
    private void fileHandle(final File file) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 打开文件
                if (which == 0) {
                    openFile(file);
                }
                //修改文件名
                else if (which == 1) {
                    LayoutInflater factory = LayoutInflater.from(FileDirectoryActivity.this);
                    view = factory.inflate(R.layout.file_directory_rename_filename_dialog, null);
                    final EditText editText = (EditText) view.findViewById(R.id.editText);
                    editText.setText(file.getName());

                    DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            String modifyName = editText.getText().toString();
                            final String fpath = file.getParentFile().getPath();
                            final File newFile = new File(fpath + "/" + modifyName);
                            if (newFile.exists()) {
                                //排除没有修改情况
                                if (!modifyName.equals(file.getName())) {
                                    new AlertDialog.Builder(FileDirectoryActivity.this)
                                            .setTitle("注意!")
                                            .setMessage("文件名已存在，是否覆盖？")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (file.renameTo(newFile)) {
                                                        showFileDir(fpath);
                                                        displayToast("重命名成功！");
                                                    } else {
                                                        displayToast("重命名失败！");
                                                    }
                                                }
                                            })
                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();
                                }
                            } else {
                                if (file.renameTo(newFile)) {
                                    showFileDir(fpath);
                                    displayToast("重命名成功！");
                                } else {
                                    displayToast("重命名失败！");
                                }
                            }
                        }
                    };
                    AlertDialog renameDialog = new AlertDialog.Builder(FileDirectoryActivity.this).create();
                    renameDialog.setView(view);
                    renameDialog.setButton("确定", listener2);
                    renameDialog.setButton2("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });
                    renameDialog.show();
                }
                //删除文件
                else {
                    new AlertDialog.Builder(FileDirectoryActivity.this)
                            .setTitle("注意!")
                            .setMessage("确定要删除此文件吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (file.delete()) {
                                        //更新文件列表
                                        showFileDir(file.getParent());
                                        displayToast("删除成功！");
                                    } else {
                                        displayToast("删除失败！");
                                    }
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            }
        };
        //选择文件时，弹出增删该操作选项对话框
        String[] menu = {"打开文件", "重命名", "删除文件"};
        new AlertDialog.Builder(FileDirectoryActivity.this)
                .setTitle("请选择要进行的操作!")
                .setItems(menu, listener)
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    //打开文件
    private void openFile(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);

        String type = getMIMEType(file);
        intent.setDataAndType(Uri.fromFile(file), type);
        startActivity(intent);
    }

    //获取文件mimetype
    private String getMIMEType(File file) {
        String type = "";
        String name = file.getName();
        //文件扩展名
        String end = name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
        if (end.equals("m4a") || end.equals("mp3") || end.equals("wav")) {
            type = "audio";
        } else if (end.equals("mp4") || end.equals("3gp")) {
            type = "video";
        } else if (end.equals("jpg") || end.equals("png") || end.equals("jpeg") || end.equals("bmp") || end.equals("gif")) {
            type = "image";
        } else {
            //如果无法直接打开，跳出列表由用户选择
            type = "*";
        }
        type += "/*";
        return type;
    }

    private void displayToast(String message) {
        Toast.makeText(FileDirectoryActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 返回时的操作
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String filePath = file_path.getText().toString();
        if (!filePath.equals(ROOT_PATH) && filePath != null) {
            if (filePath.lastIndexOf("/") == 0) {
                showFileDir(ROOT_PATH);
            } else {
                showFileDir(filePath.substring(0, filePath.lastIndexOf("/")));
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.file_path})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.file_path:
                break;
        }
    }

    @OnItemClick(R.id.file_listview)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String path = paths.get(position);
        File file = new File(path);
        // 文件存在并可读
        if (file.exists() && file.canRead()) {
            if (file.isDirectory()) {
                //显示子目录及文件
                showFileDir(path);
            } else {
                //处理文件
                fileHandle(file);
            }
        }
        //没有权限
        else {
            Resources res = getResources();
            new AlertDialog.Builder(FileDirectoryActivity.this).setTitle("Message")
                    .setMessage(res.getString(R.string.no_permission))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    }
}