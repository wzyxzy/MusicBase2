package com.musicbase.entity;

/**
 * Created by BAO on 2018-09-26.
 */

public class LoginBean {


    /**
     * code : SUCCESS
     * codeInfo : 用户登陆成功!
     * data : {"userId":2,"userPwd":"123456","userStatus":1,"userAvatar":"fengmian","nickName":"昵称","phoneId":"123456","addTime":{"date":13,"day":4,"hours":16,"minutes":26,"month":8,"seconds":12,"time":1536827172000,"timezoneOffset":-480,"year":118},"identity_type":"telphone","identifier":"18646454011"}
     */

    private String code;
    private String codeInfo;
    private Data data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setCodeInfo(String codeInfo) {
        this.codeInfo = codeInfo;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getCodeInfo() {
        return codeInfo;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        /**
         * userId : 2
         * userPwd : 123456
         * userStatus : 1
         * userAvatar : fengmian
         * nickName : 昵称
         * phoneId : 123456
         * addTime : {"date":13,"day":4,"hours":16,"minutes":26,"month":8,"seconds":12,"time":1536827172000,"timezoneOffset":-480,"year":118}
         * identity_type : telphone
         * identifier : 18646454011
         */

        private int userId;
        private String userPwd;
        private int userStatus;
        private String userAvatar;
        private String nickName;
        private String phoneId;
        private String telephone;
        private AddTime addTime;
        private String identity_type;
        private String identifier;

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setUserPwd(String userPwd) {
            this.userPwd = userPwd;
        }

        public void setUserStatus(int userStatus) {
            this.userStatus = userStatus;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public void setPhoneId(String phoneId) {
            this.phoneId = phoneId;
        }

        public void setAddTime(AddTime addTime) {
            this.addTime = addTime;
        }

        public void setIdentity_type(String identity_type) {
            this.identity_type = identity_type;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public int getUserId() {
            return userId;
        }

        public String getUserPwd() {
            return userPwd;
        }

        public int getUserStatus() {
            return userStatus;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public String getNickName() {
            return nickName;
        }

        public String getPhoneId() {
            return phoneId;
        }

        public AddTime getAddTime() {
            return addTime;
        }

        public String getIdentity_type() {
            return identity_type;
        }

        public String getIdentifier() {
            return identifier;
        }

        public static class AddTime {
            /**
             * date : 13
             * day : 4
             * hours : 16
             * minutes : 26
             * month : 8
             * seconds : 12
             * time : 1536827172000
             * timezoneOffset : -480
             * year : 118
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public void setDate(int date) {
                this.date = date;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public void setYear(int year) {
                this.year = year;
            }

            public int getDate() {
                return date;
            }

            public int getDay() {
                return day;
            }

            public int getHours() {
                return hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public int getMonth() {
                return month;
            }

            public int getSeconds() {
                return seconds;
            }

            public long getTime() {
                return time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public int getYear() {
                return year;
            }
        }
    }
}
