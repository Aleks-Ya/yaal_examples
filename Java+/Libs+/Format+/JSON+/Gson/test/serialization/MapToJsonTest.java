package serialization;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapToJsonTest {

    @Test
    void compact() {
        Map<String, Integer> map = new HashMap<>();
        map.put("abc", 123);
        map.put("Jonh", 30);

        var builder = new GsonBuilder();
        var gson = builder.create();
        var typeOfMap = new TypeToken<Map<String, Integer>>() {
        }.getType();

        var jsonStr = gson.toJson(map, typeOfMap);
        assertEquals("{\"abc\":123,\"Jonh\":30}", jsonStr);
    }
}
