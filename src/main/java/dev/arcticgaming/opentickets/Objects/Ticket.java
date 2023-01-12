package dev.arcticgaming.opentickets.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.arcticgaming.opentickets.OpenTickets;
import dev.arcticgaming.opentickets.Utils.TicketSerializer;
import dev.arcticgaming.opentickets.Utils.UUIDSerializer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Ticket {

    @Getter @Setter public UUID ticketUUID;
    @Getter @Setter public UUID playerUUID;
    @Getter @Setter public String playerName;

    @Getter @Setter String world;
    @Getter @Setter double x;
    @Getter @Setter double y;
    @Getter @Setter double z;
    @Getter @Setter public int priority;
    @Getter @Setter public String note;

    public static Map<UUID, Ticket> currentTickets = new HashMap<>();

    public Ticket(Player player, String note) {

        if (player == null) {
            this.x = 0;
            this.y = 70;
            this.z = 0;
            this.world = "world";
            this.playerName = "null Player";
            this.playerUUID = UUID.randomUUID();
        } else {
            Location location = player.getLocation();
            this.world = location.getWorld().getName();
            this.x = location.getX();
            this.y = location.getY();
            this.z = location.getZ();
            this.playerUUID = player.getUniqueId();
            this.playerName = player.getName();
        }
        this.ticketUUID = UUID.randomUUID();
        this.priority = 1;
        this.note = note;
    }

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
