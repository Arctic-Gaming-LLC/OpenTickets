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

        UUID playerUUID = UUID.fromString(jsonObject.get("playerUUID").getAsString());
        String storedNote = jsonObject.get("note").getAsString();

        Ticket ticket = new Ticket(Bukkit.getPlayer(playerUUID), storedNote);

        ticket.setTicketUUID(UUID.fromString(jsonObject.get("ticketUUID").getAsString()));

        ticket.setPlayerUUID(playerUUID);
        ticket.setWorld(jsonObject.get("world").getAsString());
        ticket.setX(jsonObject.get("x").getAsString());
        ticket.setY(jsonObject.get("y").getAsString());
        ticket.setZ(jsonObject.get("z").getAsString());
        ticket.setPriority(jsonObject.get("priority").getAsInt());
        ticket.setPlayerName(jsonObject.get("playerName").getAsString());

        return ticket;
    }
}
