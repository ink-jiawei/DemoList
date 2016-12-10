package com.hjw.diagramview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * @author hjw
 * @deprecated 自定义饼状图显示的Activity
 */
public class DiagramActivity extends AppCompatActivity {
    private DiagramView diagram_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram);

        diagram_view = (DiagramView) findViewById(R.id.diagram_view);
        diagram_view.setData(initData());
    }

    public ArrayList initData() {
        ArrayList<DataBean> list = new ArrayList<>();
        list.add(new DataBean("11111", 20));
        list.add(new DataBean("22222", 30));
        list.add(new DataBean("33333", 40));
        list.add(new DataBean("44444", 10));
        return list;
    }
}
