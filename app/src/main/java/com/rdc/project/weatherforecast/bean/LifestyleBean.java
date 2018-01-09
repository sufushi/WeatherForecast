package com.rdc.project.weatherforecast.bean;

public class LifestyleBean {
    /**
     * brf : 舒适
     * txt : 白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。
     * type : comf
     */

    private String brf;
    private String txt;
    private String type;

    public String getBrf() {
        return brf;
    }

    public void setBrf(String brf) {
        this.brf = brf;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LifestyleBean{" +
                "brf='" + brf + '\'' +
                ", txt='" + txt + '\'' +
                ", type='" + type + '\'' +
                '}' + '\n';
    }
}