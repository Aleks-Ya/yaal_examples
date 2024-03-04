package gptui.model.storage;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

class InteractionIdSerDe implements JsonSerializer<InteractionId>, JsonDeserializer<InteractionId> {
    @Override
    public JsonElement serialize(InteractionId interactionId, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(interactionId.id());
    }

    @Override
    public InteractionId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new InteractionId(json.getAsLong());
    }
}
