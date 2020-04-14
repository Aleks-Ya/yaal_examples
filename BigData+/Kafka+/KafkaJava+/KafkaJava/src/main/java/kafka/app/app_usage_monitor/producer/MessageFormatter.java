package kafka.app.app_usage_monitor.producer;

import com.google.gson.Gson;
import kafka.app.app_usage_monitor.common.AppInfo;
import kafka.app.app_usage_monitor.common.AppTrackInfo;

class MessageFormatter {
    private static final Gson gson = new Gson();

    public String toJson(AppInfo appInfo, long beginTime, long endTime) {
        return gson.toJson(new AppTrackInfo(appInfo, beginTime, endTime));
    }
}
