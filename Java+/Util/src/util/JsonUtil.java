package util;

public class JsonUtil {

    private JsonUtil() {
    }

    public static String singleQuoteToDouble(String singleQuoteJson) {
        if (singleQuoteJson == null) {
            return null;
        }
        return singleQuoteJson.replace("'", "\"");
    }
}
