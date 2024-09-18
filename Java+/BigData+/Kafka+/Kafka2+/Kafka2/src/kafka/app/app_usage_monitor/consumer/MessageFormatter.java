package kafka.app.app_usage_monitor.consumer;

import com.google.gson.Gson;
import kafka.app.app_usage_monitor.common.AppTrackInfo;

class MessageFormatter {
    private static final Gson gson = new Gson();

    public AppTrackInfo fromJson(String json) {
        return gson.fromJson(json, AppTrackInfo.class);
    }

}
