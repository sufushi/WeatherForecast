package com.rdc.project.weatherforecast.bean;

import java.util.List;

public class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101280101","location":"广州","parent_city":"广州","admin_area":"广东","cnty":"中国","lat":"23.12517738","lon":"113.28063965","tz":"+8.0"}
         * update : {"loc":"2017-12-12 15:52","utc":"2017-12-12 07:52"}
         * status : ok
         * now : {"cloud":"0","cond_code":"101","cond_txt":"多云","fl":"17","hum":"34","pcpn":"0","pres":"1017","tmp":"20","vis":"10","wind_deg":"57","wind_dir":"东北风","wind_sc":"微风","wind_spd":"4"}
         * daily_forecast : [{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2017-12-12","hum":"33","mr":"01:35","ms":"13:55","pcpn":"0.0","pop":"0","pres":"1020","sr":"06:58","ss":"17:42","tmp_max":"21","tmp_min":"15","uv_index":"3","vis":"20","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"8"},{"cond_code_d":"305","cond_code_n":"305","cond_txt_d":"小雨","cond_txt_n":"小雨","date":"2017-12-13","hum":"43","mr":"02:27","ms":"14:32","pcpn":"0.0","pop":"0","pres":"1019","sr":"06:59","ss":"17:43","tmp_max":"20","tmp_min":"15","uv_index":"5","vis":"20","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"6"},{"cond_code_d":"104","cond_code_n":"101","cond_txt_d":"阴","cond_txt_n":"多云","date":"2017-12-14","hum":"51","mr":"03:19","ms":"15:09","pcpn":"0.0","pop":"0","pres":"1020","sr":"06:59","ss":"17:43","tmp_max":"19","tmp_min":"14","uv_index":"6","vis":"20","wind_deg":"350","wind_dir":"北风","wind_sc":"3-4","wind_spd":"17"}]
         * lifestyle : [{"brf":"舒适","txt":"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。","type":"comf"},{"brf":"较舒适","txt":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。","type":"drsg"},{"brf":"少发","txt":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。","type":"flu"},{"brf":"适宜","txt":"天气较好，赶快投身大自然参与户外运动，尽情感受运动的快乐吧。","type":"sport"},{"brf":"适宜","txt":"天气较好，但丝毫不会影响您出行的心情。温度适宜又有微风相伴，适宜旅游。","type":"trav"},{"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。","type":"uv"},{"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。","type":"cw"},{"brf":"良","txt":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。","type":"air"}]
         */

        private BasicBean basic;
        private UpdateBean update;
        private String status;
        private NowBean now;
        private List<DailyForecastBean> daily_forecast;
        private List<LifestyleBean> lifestyle;

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

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public List<DailyForecastBean> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public List<LifestyleBean> getLifestyle() {
            return lifestyle;
        }

        public void setLifestyle(List<LifestyleBean> lifestyle) {
            this.lifestyle = lifestyle;
        }

    @Override
    public String toString() {
        return "HeWeather6Bean{" +
                "basic=" + basic +
                ", update=" + update +
                ", status='" + status + '\'' +
                ", now=" + now +
                ", daily_forecast=" + daily_forecast +
                ", lifestyle=" + lifestyle +
                '}' + '\n';
    }
}