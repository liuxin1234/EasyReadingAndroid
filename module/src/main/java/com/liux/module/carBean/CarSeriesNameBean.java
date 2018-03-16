package com.liux.module.carBean;

import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.module.carBean
 * 项目日期：2017/12/4
 * 作者：liux
 * 功能：
 */

public class CarSeriesNameBean {

    /**
     * msg : success
     * result : [{"brandName":"奥迪Q5","carId":"1060133","guidePrice":"38.34万","seriesName":"奥迪Q52016款40TFSI进取型"},{"brandName":"奥迪Q5","carId":"1060134","guidePrice":"42.76万","seriesName":"奥迪Q52016款40TFSI技术型"},{"brandName":"奥迪Q5","carId":"1060135","guidePrice":"47.90万","seriesName":"奥迪Q52016款40TFSI舒适型"},{"brandName":"奥迪Q5","carId":"1060136","guidePrice":"50.90万","seriesName":"奥迪Q52016款40TFSI动感型plus"},{"brandName":"奥迪Q5","carId":"1060137","guidePrice":"53.40万","seriesName":"奥迪Q52016款40TFSI豪华型plus"}]
     * retCode : 200
     */

    private String msg;
    private String retCode;
    private List<ResultBean> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * brandName : 奥迪Q5
         * carId : 1060133
         * guidePrice : 38.34万
         * seriesName : 奥迪Q52016款40TFSI进取型
         */

        private String brandName;
        private String carId;
        private String guidePrice;
        private String seriesName;

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public String getGuidePrice() {
            return guidePrice;
        }

        public void setGuidePrice(String guidePrice) {
            this.guidePrice = guidePrice;
        }

        public String getSeriesName() {
            return seriesName;
        }

        public void setSeriesName(String seriesName) {
            this.seriesName = seriesName;
        }
    }
}
