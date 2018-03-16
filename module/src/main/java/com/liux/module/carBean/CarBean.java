package com.liux.module.carBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.module.CarBean
 * 项目日期：2017/12/1
 * 作者：liux
 * 功能：
 */

public class CarBean implements Serializable{

    /**
     * msg : success
     * retCode : 200
     * result : [{"name":"AC Schnitzer","son":[{"type":"AC Schnitzer X5","car":"AC Schnitzer"}]}]
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

    public static class ResultBean implements Serializable{
        /**
         * name : AC Schnitzer
         * son : [{"type":"AC Schnitzer X5","car":"AC Schnitzer"}]
         */

        private String name;
        private List<SonBean> son;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<SonBean> getSon() {
            return son;
        }

        public void setSon(List<SonBean> son) {
            this.son = son;
        }

        public static class SonBean implements Serializable{
            /**
             * type : AC Schnitzer X5
             * car : AC Schnitzer
             */

            private String type;
            private String car;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCar() {
                return car;
            }

            public void setCar(String car) {
                this.car = car;
            }
        }
    }
}
