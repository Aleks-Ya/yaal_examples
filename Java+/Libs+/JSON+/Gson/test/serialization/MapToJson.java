package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapToJson {

    @Test
    public void compact() {
        Map<String, Integer> map = new HashMap<>();
        map.put("abc", 123);
        map.put("Jonh", 30);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type typeOfMap = new TypeToken<Map<String, Integer>>() {
        }.getType();

        String jsonStr = gson.toJson(map, typeOfMap);
        assertEquals("{\"abc\":123,\"Jonh\":30}", jsonStr);
    }
}
