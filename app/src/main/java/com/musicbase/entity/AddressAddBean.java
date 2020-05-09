package com.musicbase.entity;

public class AddressAddBean {


    /**
     * code : SUCCESS
     * codeInfo : 操作成功!
     * data : {"id":"18","userId":"2","userName":"aaaaaaaaa","telphone":"18646454011","areaCode":"110101","address":"dsadadd","isDefault":"0","areaName":"中国北京北京市东城区"}
     */

    private String code;
    private String codeInfo;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeInfo() {
        return codeInfo;
    }

    public void setCodeInfo(String codeInfo) {
        this.codeInfo = codeInfo;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 18
         * userId : 2
         * userName : aaaaaaaaa
         * telphone : 18646454011
         * areaCode : 110101
         * address : dsadadd
         * isDefault : 0
         * areaName : 中国北京北京市东城区
         */

        private String id;
        private String userId;
        private String userName;
        private String telphone;
        private String areaCode;
        private String address;
        private String isDefault;
        private String areaName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }
    }
}
