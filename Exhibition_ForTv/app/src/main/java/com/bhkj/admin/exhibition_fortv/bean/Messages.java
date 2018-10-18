package com.bhkj.admin.exhibition_fortv.bean;

/**
 * Created by Administrator on 2018/10/17.
 */

public class Messages {
    private int type;
    private String data;

    public Messages(int type, String data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
