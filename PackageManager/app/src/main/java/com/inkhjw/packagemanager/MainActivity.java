package com.inkhjw.packagemanager;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private ListView lv;

    AppAdapter adapter;
    List<PackageInfo> lists;

    private PackageManager pm;
    private static final int All_APP = 0;
    private static final int SYSTEM_APP = 1;
    private static final int THIRD_APP = 2;
    private static final int SD_CARD_APP = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();

        pm = getPackageManager();
        lists = new ArrayList<>();
        adapter = new AppAdapter(lists, this);
        lv.setAdapter(adapter);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.notify(All_APP);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.notify(SYSTEM_APP);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.notify(THIRD_APP);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.notify(SD_CARD_APP);
            }
        });
    }

    protected void findView() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        lv = (ListView) findViewById(R.id.lv);
    }

    protected void notify(int flag) {
        lists.clear();
        lists.addAll(getAppInfos(flag));
        adapter.notifyDataSetChanged();
    }

    private List<PackageInfo> getAppInfos(int flag) {
        List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);

        List<PackageInfo> apps = new ArrayList<>();
        switch (flag) {
            /**
             * app判定规则
             */
            case All_APP:
                for (ApplicationInfo app : applicationInfos) {
                    apps.add(makeAppInfo(app));
                }
                break;

            case SYSTEM_APP:
                for (ApplicationInfo app : applicationInfos) {
                    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        apps.add(makeAppInfo(app));
                    }
                }
                break;

            case THIRD_APP:
                for (ApplicationInfo app : applicationInfos) {
                    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                        apps.add(makeAppInfo(app));
                    } else if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                        apps.add(makeAppInfo(app));
                    }
                }
                break;

            case SD_CARD_APP:
                for (ApplicationInfo app : applicationInfos) {
                    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                        apps.add(makeAppInfo(app));
                    } else if ((app.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                        apps.add(makeAppInfo(app));
                    }
                }
                break;
            default:
                return null;
        }

        return apps;
    }

    private PackageInfo makeAppInfo(ApplicationInfo app) {
        PackageInfo packageInfo = new PackageInfo();
        packageInfo.setAppLabel((String) app.loadLabel(pm));
        packageInfo.setAppIcon(app.loadIcon(pm));
        packageInfo.setAppPackageName(app.packageName);
        return packageInfo;
    }
}
