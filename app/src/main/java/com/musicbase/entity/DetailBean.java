package com.musicbase.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by BAO on 2018-09-28.
 */

public class DetailBean implements Serializable {


    /**
     * code : SUCCESS
     * codeInfo : 操作成功!
     * data : {"courseId":1,"courseName":"测试","courseBookType":1,"courseImgPathHorizontal":"2019/7/26/images/cf32e658-f3af-4dca-bee3-234172a994dc.jpg","courseImgPathVertical":"2019/7/26/images/0e0bbf41-e7ff-4b04-8023-ddcb744472ff.png","courseAuthor":"阿萨德","courseAuthorIntro":"非法的说法而无法是虚荣噶尔噶而过时大V无法让我哥上庭辩护听不听一卡通过我有两个偷看她惊讶地非法的说法而无法是虚荣噶尔噶而过时大V无法让我哥上庭辩护听不听一卡通过我有两个偷看她惊讶地非法的说法而无法是虚荣噶尔噶而过时大V无法让我哥上庭辩护听不听一卡通过我有两个偷看她惊讶地","courseRemarks":"返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头","classification":1,"chargeType":1,"originalPrice":0.01,"currentPrice":0.01,"isPack":0,"courseIntro":"返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头","courseHighlight":"返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头","testPaperTime":6,"courseIsPay":1,"resourceList":[{"resourceId":7,"resourceType":"testPaper","resourceFileName":"综合模拟初级03","resourceSize":22093841,"resourceSaveName":"ffafdf25-a76f-46de-bde4-88e5a74139f0.zip","resourcePath":"2019/7/26/attachs/","chargeType":1,"originalPrice":"0.01","currentPrice":"0.01","isDismount":0,"isFolder":0,"isPay":1},{"resourceId":6,"resourceType":"testPaper","resourceFileName":"综合模拟初级02","resourceSize":23131403,"resourceSaveName":"4135ae1e-44ca-4cdb-a23b-ec4fae9d6ca3.zip","resourcePath":"2019/7/26/attachs/","chargeType":1,"originalPrice":"0.01","currentPrice":"0.01","isDismount":0,"isFolder":0,"isPay":1},{"resourceId":5,"resourceType":"testPaper","resourceFileName":"综合模拟初级01","resourceSize":21185603,"resourceSaveName":"6aa906db-8cb1-4ff1-9f33-071b8197acd8.zip","resourcePath":"2019/7/26/attachs/","chargeType":0,"originalPrice":"0.01","currentPrice":"0.01","isDismount":0,"isFolder":0,"isPay":0},{"resourceId":1,"resourceType":"folder","resourceFileName":"第一课","resourceSize":0,"chargeType":1,"originalPrice":"0.01","currentPrice":"0.01","isDismount":0,"isFolder":1,"isPay":1}],"informationList":[],"moduleList":[]}
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
         * courseId : 1
         * courseName : 测试
         * courseBookType : 1
         * courseImgPathHorizontal : 2019/7/26/images/cf32e658-f3af-4dca-bee3-234172a994dc.jpg
         * courseImgPathVertical : 2019/7/26/images/0e0bbf41-e7ff-4b04-8023-ddcb744472ff.png
         * courseAuthor : 阿萨德
         * courseAuthorIntro : 非法的说法而无法是虚荣噶尔噶而过时大V无法让我哥上庭辩护听不听一卡通过我有两个偷看她惊讶地非法的说法而无法是虚荣噶尔噶而过时大V无法让我哥上庭辩护听不听一卡通过我有两个偷看她惊讶地非法的说法而无法是虚荣噶尔噶而过时大V无法让我哥上庭辩护听不听一卡通过我有两个偷看她惊讶地
         * courseRemarks : 返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头
         * classification : 1
         * chargeType : 1
         * originalPrice : 0.01
         * currentPrice : 0.01
         * isPack : 0
         * courseIntro : 返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头
         * courseHighlight : 返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头返回个你媳妇归园田居送人头
         * testPaperTime : 6
         * courseIsPay : 1
         * resourceList : [{"resourceId":7,"resourceType":"testPaper","resourceFileName":"综合模拟初级03","resourceSize":22093841,"resourceSaveName":"ffafdf25-a76f-46de-bde4-88e5a74139f0.zip","resourcePath":"2019/7/26/attachs/","chargeType":1,"originalPrice":"0.01","currentPrice":"0.01","isDismount":0,"isFolder":0,"isPay":1},{"resourceId":6,"resourceType":"testPaper","resourceFileName":"综合模拟初级02","resourceSize":23131403,"resourceSaveName":"4135ae1e-44ca-4cdb-a23b-ec4fae9d6ca3.zip","resourcePath":"2019/7/26/attachs/","chargeType":1,"originalPrice":"0.01","currentPrice":"0.01","isDismount":0,"isFolder":0,"isPay":1},{"resourceId":5,"resourceType":"testPaper","resourceFileName":"综合模拟初级01","resourceSize":21185603,"resourceSaveName":"6aa906db-8cb1-4ff1-9f33-071b8197acd8.zip","resourcePath":"2019/7/26/attachs/","chargeType":0,"originalPrice":"0.01","currentPrice":"0.01","isDismount":0,"isFolder":0,"isPay":0},{"resourceId":1,"resourceType":"folder","resourceFileName":"第一课","resourceSize":0,"chargeType":1,"originalPrice":"0.01","currentPrice":"0.01","isDismount":0,"isFolder":1,"isPay":1}]
         * informationList : []
         * moduleList : []
         */

        private int courseId;
        private int isPay;
        public String courseName;
        public String message;
        private int courseBookType;
        public String courseImgPathHorizontal;
        public String courseImgPathVertical;
        public String courseAuthor;
        public String courseAuthorIntro;
        public String courseRemarks;
        private int classification;
        private int chargeType;
        private String originalPrice;
        private String currentPrice;
        private int isPack;
        public String courseIntro;
        public String courseUrl;
        public String endTime;
        public String catalogue;
        public String catalogueImgPath;
        public String residueStock;
        public String courseHighlight;
        private int testPaperTime;
        private int courseIsPay;
        private List<ResourceListBean> resourceList;
        private List<InformationList> informationList;
        private List<ModuleList> moduleList;

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getIsPay() {
            return isPay;
        }

        public void setIsPay(int isPay) {
            this.isPay = isPay;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public String getCurrentPrice() {
            return currentPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public void setCurrentPrice(String currentPrice) {
            this.currentPrice = currentPrice;
        }

        public String getCourseUrl() {
            return courseUrl;
        }

        public void setCourseUrl(String courseUrl) {
            this.courseUrl = courseUrl;
        }

        public String getCatalogue() {
            return catalogue;
        }

        public void setCatalogue(String catalogue) {
            this.catalogue = catalogue;
        }

        public String getCatalogueImgPath() {
            return catalogueImgPath;
        }

        public void setCatalogueImgPath(String catalogueImgPath) {
            this.catalogueImgPath = catalogueImgPath;
        }

        public String getResidueStock() {
            return residueStock;
        }

        public void setResidueStock(String residueStock) {
            this.residueStock = residueStock;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getCourseBookType() {
            return courseBookType;
        }

        public void setCourseBookType(int courseBookType) {
            this.courseBookType = courseBookType;
        }

        public String getCourseImgPathHorizontal() {
            return courseImgPathHorizontal;
        }

        public void setCourseImgPathHorizontal(String courseImgPathHorizontal) {
            this.courseImgPathHorizontal = courseImgPathHorizontal;
        }

        public String getCourseImgPathVertical() {
            return courseImgPathVertical;
        }

        public void setCourseImgPathVertical(String courseImgPathVertical) {
            this.courseImgPathVertical = courseImgPathVertical;
        }

        public String getCourseAuthor() {
            return courseAuthor;
        }

        public void setCourseAuthor(String courseAuthor) {
            this.courseAuthor = courseAuthor;
        }

        public String getCourseAuthorIntro() {
            return courseAuthorIntro;
        }

        public void setCourseAuthorIntro(String courseAuthorIntro) {
            this.courseAuthorIntro = courseAuthorIntro;
        }

        public String getCourseRemarks() {
            return courseRemarks;
        }

        public void setCourseRemarks(String courseRemarks) {
            this.courseRemarks = courseRemarks;
        }

        public int getClassification() {
            return classification;
        }

        public void setClassification(int classification) {
            this.classification = classification;
        }

        public int getChargeType() {
            return chargeType;
        }

        public void setChargeType(int chargeType) {
            this.chargeType = chargeType;
        }

        public int getIsPack() {
            return isPack;
        }

        public void setIsPack(int isPack) {
            this.isPack = isPack;
        }

        public String getCourseIntro() {
            return courseIntro;
        }

        public void setCourseIntro(String courseIntro) {
            this.courseIntro = courseIntro;
        }

        public String getCourseHighlight() {
            return courseHighlight;
        }

        public void setCourseHighlight(String courseHighlight) {
            this.courseHighlight = courseHighlight;
        }

        public int getTestPaperTime() {
            return testPaperTime;
        }

        public void setTestPaperTime(int testPaperTime) {
            this.testPaperTime = testPaperTime;
        }

        public int getCourseIsPay() {
            return courseIsPay;
        }

        public void setCourseIsPay(int courseIsPay) {
            this.courseIsPay = courseIsPay;
        }

        public List<ResourceListBean> getResourceList() {
            return resourceList;
        }

        public void setResourceList(List<ResourceListBean> resourceList) {
            this.resourceList = resourceList;
        }

        public List<InformationList> getInformationList() {
            return informationList;
        }

        public void setInformationList(List<InformationList> informationList) {
            this.informationList = informationList;
        }

        public List<ModuleList> getModuleList() {
            return moduleList;
        }

        public void setModuleList(List<ModuleList> moduleList) {
            this.moduleList = moduleList;
        }

        public static class ResourceListBean {
            /**
             * resourceId : 7
             * resourceType : testPaper
             * resourceFileName : 综合模拟初级03
             * resourceSize : 22093841
             * resourceSaveName : ffafdf25-a76f-46de-bde4-88e5a74139f0.zip
             * resourcePath : 2019/7/26/attachs/
             * chargeType : 1
             * originalPrice : 0.01
             * currentPrice : 0.01
             * isDismount : 0
             * isFolder : 0
             * isPay : 1
             */

            private int resourceId;
            private String resourceType;
            private String resourceFileName;
            private int resourceSize;
            private String resourceSaveName;
            private String resourcePath;
            private int chargeType;
            private String originalPrice;
            private String currentPrice;
            private String bunchPlant;
            private String onlineLinks;
            private int isDismount;
            private int isFolder;
            private int isPay;

            public int getResourceId() {
                return resourceId;
            }

            public void setResourceId(int resourceId) {
                this.resourceId = resourceId;
            }

            public String getResourceType() {
                return resourceType;
            }

            public void setResourceType(String resourceType) {
                this.resourceType = resourceType;
            }

            public String getResourceFileName() {
                return resourceFileName;
            }

            public void setResourceFileName(String resourceFileName) {
                this.resourceFileName = resourceFileName;
            }

            public int getResourceSize() {
                return resourceSize;
            }

            public void setResourceSize(int resourceSize) {
                this.resourceSize = resourceSize;
            }

            public String getResourceSaveName() {
                return resourceSaveName;
            }

            public void setResourceSaveName(String resourceSaveName) {
                this.resourceSaveName = resourceSaveName;
            }

            public String getResourcePath() {
                return resourcePath;
            }

            public void setResourcePath(String resourcePath) {
                this.resourcePath = resourcePath;
            }

            public int getChargeType() {
                return chargeType;
            }

            public void setChargeType(int chargeType) {
                this.chargeType = chargeType;
            }

            public String getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(String originalPrice) {
                this.originalPrice = originalPrice;
            }

            public String getCurrentPrice() {
                return currentPrice;
            }

            public void setCurrentPrice(String currentPrice) {
                this.currentPrice = currentPrice;
            }

            public String getBunchPlant() {
                return bunchPlant;
            }

            public void setBunchPlant(String bunchPlant) {
                this.bunchPlant = bunchPlant;
            }

            public String getOnlineLinks() {
                return onlineLinks;
            }

            public void setOnlineLinks(String onlineLinks) {
                this.onlineLinks = onlineLinks;
            }

            public int getIsDismount() {
                return isDismount;
            }

            public void setIsDismount(int isDismount) {
                this.isDismount = isDismount;
            }

            public int getIsFolder() {
                return isFolder;
            }

            public void setIsFolder(int isFolder) {
                this.isFolder = isFolder;
            }

            public int getIsPay() {
                return isPay;
            }

            public void setIsPay(int isPay) {
                this.isPay = isPay;
            }
        }

        public static class InformationList implements Serializable {
            private int informationId;
            private int category;    //类型，0代表的是对外网络链接；1代表的是对内的课程跳转
            private String title;
            private String links;    //category为0时返回的是url；category为1时返回的是课程ID（courseId）

            public int getInformationId() {
                return informationId;
            }

            public void setInformationId(int informationId) {
                this.informationId = informationId;
            }

            public int getCategory() {
                return category;
            }

            public void setCategory(int category) {
                this.category = category;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLinks() {
                return links;
            }

            public void setLinks(String links) {
                this.links = links;
            }
        }

        public static class ModuleList implements Serializable {
            private String moduleName;
            private String moduleField;

            public String getModuleName() {
                return moduleName;
            }

            public void setModuleName(String moduleName) {
                this.moduleName = moduleName;
            }

            public String getModuleField() {
                return moduleField;
            }

            public void setModuleField(String moduleField) {
                this.moduleField = moduleField;
            }
        }
    }
}
