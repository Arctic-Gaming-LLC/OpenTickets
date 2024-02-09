package dev.arcticgaming.opentickets.Utils;

import com.google.gson.*;
import dev.arcticgaming.opentickets.Objects.Ticket;
import org.bukkit.Bukkit;

import java.lang.reflect.Type;
import java.util.UUID;

public class TicketDeserializer implements JsonDeserializer<Ticket> {

    @Override
    public Ticket deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        UUID ticketUUID = UUID.fromString(jsonObject.get("ticketUUID").getAsString());
        String ticketName = jsonObject.get("ticketName").getAsString();
        UUID playerUUID = UUID.fromString(jsonObject.get("playerUUID").getAsString());
        String playerName = jsonObject.get("playerName").getAsString();
        String location = jsonObject.get("location").getAsString();
        String supportGroup = jsonObject.get("supportGroup").getAsString();
        String note = jsonObject.get("note").getAsString();

        return new Ticket(ticketUUID, ticketName, playerUUID, playerName, location, supportGroup, note);
    }
}
