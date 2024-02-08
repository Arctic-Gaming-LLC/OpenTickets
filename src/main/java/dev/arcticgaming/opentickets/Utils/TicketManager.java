package dev.arcticgaming.opentickets.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.OpenTickets;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TicketManager {

    public static Map<UUID, Ticket> currentTickets = new HashMap<>();
    public static void createTicket(Player player, String note){
        Ticket newTicket = new Ticket(player, note);

        currentTickets.put(newTicket.ticketUUID, newTicket);
        try {
            saveTickets();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void saveTickets() throws IOException {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UUID.class, new UUIDSerializer())
                .registerTypeAdapter(Ticket.class, new TicketSerializer())
                .setPrettyPrinting()
                .create();

        File file = new File(OpenTickets.getPlugin(OpenTickets.class).getDataFolder().getAbsolutePath()+"/tickets.json");

        if(!file.exists()){
            file.getParentFile().mkdirs();
        }
        file.createNewFile();

        if (!currentTickets.isEmpty()) {
            Writer writer = new FileWriter(file, false);

            gson.toJson(currentTickets, writer);
            writer.flush();
            writer.close();
        } else {
            file.delete();
            OpenTickets.getPlugin().getLogger().info("Save Tickets was empty - deleted tickets.json");
        }
    }

    public static void closeTicket(Ticket ticket) {
        currentTickets.remove(ticket.ticketUUID);
        try {
            saveTickets();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
