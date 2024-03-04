package gptui.model.storage;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

class ThemeIdSerDe implements JsonSerializer<ThemeId>, JsonDeserializer<ThemeId> {
    @Override
    public JsonElement serialize(ThemeId themeId, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(themeId.id());
    }

    @Override
    public ThemeId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new ThemeId(json.getAsLong());
    }
}
