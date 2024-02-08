package dev.arcticgaming.opentickets.Utils;

import com.google.gson.*;
import dev.arcticgaming.opentickets.Objects.Ticket;

import java.lang.reflect.Type;

public class TicketSerializer implements JsonSerializer<Ticket> {
    @Override
    public JsonElement serialize(Ticket ticket, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("ticketUUID", new JsonPrimitive(ticket.getTicketUUID().toString()));
        jsonObject.add("playerUUID", new JsonPrimitive(ticket.getPlayerUUID().toString()));
        jsonObject.add("playerName", new JsonPrimitive(ticket.getPlayerName()));
        jsonObject.add("location", new JsonPrimitive(ticket.getLocation()));
        jsonObject.add("supportGroup", new JsonPrimitive(ticket.getSupportGroup()));
        jsonObject.add("note", new JsonPrimitive(ticket.getNote()));
        return jsonObject;
    }
}
