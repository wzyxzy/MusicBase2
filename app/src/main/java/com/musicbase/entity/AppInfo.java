package com.musicbase.entity;

public class AppInfo {
    private String AppName;
    private String AppVersion;
    private String Platform;

    public AppInfo(String appName, String appVersion, String platform) {
        AppName = appName;
        AppVersion = appVersion;
        Platform = platform;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getPlatform() {
        return Platform;
    }

    public void setPlatform(String platform) {
        Platform = platform;
    }
}
