package dev.arcticgaming.opentickets.Utils;

import java.util.UUID;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UUIDSerializer implements JsonSerializer<UUID> {
    @Override
    public JsonElement serialize(UUID src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
