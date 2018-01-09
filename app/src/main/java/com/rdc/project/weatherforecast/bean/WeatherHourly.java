package com.rdc.project.weatherforecast.bean;

import java.util.List;

public class WeatherHourly {

    private List<HeWeather6Bean> HeWeather6;

    public List<HeWeather6Bean> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<HeWeather6Bean> HeWeather6) {
        this.HeWeather6 = HeWeather6;
    }

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101280101","location":"广州","parent_city":"广州","admin_area":"广东","cnty":"中国","lat":"23.12517738","lon":"113.28063965","tz":"+8.0"}
         * update : {"loc":"2017-12-14 10:52","utc":"2017-12-14 02:52"}
         * status : ok
         * hourly : [{"cloud":"16","cond_code":"103","cond_txt":"晴间多云","dew":"12","hum":"45","pop":"0","pres":"1019","time":"2017-12-14 13:00","tmp":"18","wind_deg":"85","wind_dir":"东风","wind_sc":"微风","wind_spd":"8"},{"cloud":"17","cond_code":"103","cond_txt":"晴间多云","dew":"12","hum":"42","pop":"0","pres":"1018","time":"2017-12-14 16:00","tmp":"19","wind_deg":"92","wind_dir":"东风","wind_sc":"微风","wind_spd":"8"},{"cloud":"21","cond_code":"103","cond_txt":"晴间多云","dew":"12","hum":"43","pop":"0","pres":"1020","time":"2017-12-14 19:00","tmp":"18","wind_deg":"59","wind_dir":"东北风","wind_sc":"微风","wind_spd":"5"},{"cloud":"36","cond_code":"103","cond_txt":"晴间多云","dew":"12","hum":"50","pop":"0","pres":"1021","time":"2017-12-14 22:00","tmp":"15","wind_deg":"86","wind_dir":"东风","wind_sc":"微风","wind_spd":"4"},{"cloud":"93","cond_code":"104","cond_txt":"阴","dew":"14","hum":"60","pop":"0","pres":"1020","time":"2017-12-15 01:00","tmp":"15","wind_deg":"147","wind_dir":"东南风","wind_sc":"微风","wind_spd":"4"},{"cloud":"93","cond_code":"104","cond_txt":"阴","dew":"14","hum":"65","pop":"46","pres":"1020","time":"2017-12-15 04:00","tmp":"14","wind_deg":"115","wind_dir":"东南风","wind_sc":"微风","wind_spd":"4"},{"cloud":"80","cond_code":"305","cond_txt":"小雨","dew":"14","hum":"66","pop":"22","pres":"1021","time":"2017-12-15 07:00","tmp":"14","wind_deg":"67","wind_dir":"东北风","wind_sc":"微风","wind_spd":"6"},{"cloud":"64","cond_code":"101","cond_txt":"多云","dew":"14","hum":"58","pop":"0","pres":"1021","time":"2017-12-15 10:00","tmp":"15","wind_deg":"49","wind_dir":"东北风","wind_sc":"微风","wind_spd":"7"}]
         */

        private BasicBean basic;
        private UpdateBean update;
        private String status;
        private List<HourlyBean> hourly;

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<HourlyBean> getHourly() {
            return hourly;
        }

        public void setHourly(List<HourlyBean> hourly) {
            this.hourly = hourly;
        }


    }
}
