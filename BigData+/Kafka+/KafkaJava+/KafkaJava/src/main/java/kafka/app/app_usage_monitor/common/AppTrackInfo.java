package kafka.app.app_usage_monitor.common;

public class AppTrackInfo {
    private final AppInfo appInfo;
    private final long beginTime;
    private final long endTime;

    public AppTrackInfo(AppInfo appInfo, long beginTime, long endTime) {
        this.appInfo = appInfo;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
