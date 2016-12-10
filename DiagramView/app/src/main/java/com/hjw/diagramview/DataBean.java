package com.hjw.diagramview;

/**
 * @author hjw
 * @deprecated
 */
public class DataBean {
    private String text;
    private float num;//百分比比例

    public DataBean(String text, float num) {
        this.text = text;
        this.num = num;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }
}
