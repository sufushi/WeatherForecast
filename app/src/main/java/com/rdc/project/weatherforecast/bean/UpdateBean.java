package com.rdc.project.weatherforecast.bean;

public class UpdateBean {
    /**
     * loc : 2017-12-12 15:52
     * utc : 2017-12-12 07:52
     */

    private String loc;
    private String utc;

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }

    @Override
    public String toString() {
        return "UpdateBean{" +
                "loc='" + loc + '\'' +
                ", utc='" + utc + '\'' +
                '}' + '\n';
    }
}