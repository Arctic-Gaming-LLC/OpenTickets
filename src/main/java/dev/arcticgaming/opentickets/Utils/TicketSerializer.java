package dev.arcticgaming.opentickets.Utils;

import com.google.gson.*;
import dev.arcticgaming.opentickets.Objects.Ticket;

import java.lang.reflect.Type;
import java.util.Map;

public class TicketSerializer implements JsonSerializer<Ticket> {
    @Override
    public JsonElement serialize(Ticket ticket, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("ticketUUID", new JsonPrimitive(ticket.getTicketUUID().toString()));
        jsonObject.add("ticketName", new JsonPrimitive(ticket.getTicketName()));
        jsonObject.add("playerUUID", new JsonPrimitive(ticket.getPlayerUUID().toString()));
        jsonObject.add("playerName", new JsonPrimitive(ticket.getPlayerName()));
        jsonObject.add("location", new JsonPrimitive(ticket.getLocation()));
        jsonObject.add("supportGroup", new JsonPrimitive(ticket.getSupportGroup()));
        jsonObject.add("description", new JsonPrimitive(ticket.getDescription()));

        // Serializing the HashMap called notes
        JsonObject notesObject = new JsonObject();
        Map<String, String> notes = ticket.getNotes(); // Assuming Ticket class has a getNotes method
        if (notes != null) {
            for (Map.Entry<String, String> entry : notes.entrySet()) {
                notesObject.add(entry.getKey(), new JsonPrimitive(entry.getValue()));
            }
        }
        jsonObject.add("notes", notesObject);

        return jsonObject;
    }
}
