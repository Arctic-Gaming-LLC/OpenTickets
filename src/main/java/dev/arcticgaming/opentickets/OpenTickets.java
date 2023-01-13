package dev.arcticgaming.opentickets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.arcticgaming.opentickets.Commands.createTicketCommand;
import dev.arcticgaming.opentickets.Commands.viewTicketsCommand;
import dev.arcticgaming.opentickets.Listeners.InventoryClickEventListener;
import dev.arcticgaming.opentickets.Listeners.PlayerLoginEventListener;
import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.Utils.TicketDeserializer;
import dev.arcticgaming.opentickets.Utils.UUIDDeserializer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class OpenTickets extends JavaPlugin implements Listener {

    @Getter
    public static OpenTickets plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        Objects.requireNonNull(getCommand("createTicket")).setExecutor(new createTicketCommand());
        Objects.requireNonNull(getCommand("viewTickets")).setExecutor(new viewTicketsCommand());

        Bukkit.getPluginManager().registerEvents(this,this);
        final PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new InventoryClickEventListener(), this);
        pm.registerEvents(new PlayerLoginEventListener(), this);

        try {
            initTickets();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    public static void initTickets() throws FileNotFoundException {

        Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDDeserializer()).registerTypeAdapter(Ticket.class, new TicketDeserializer()).setPrettyPrinting().create();
        File file = new File(OpenTickets.getPlugin().getDataFolder().getAbsolutePath() + "/tickets.json");

        if (file.exists()) {

            Reader reader = new FileReader(file);

            Type type = new TypeToken<HashMap<UUID, Ticket>>() {}.getType();
            HashMap<UUID, Ticket> clonedMap = gson.fromJson(reader, type);

                for (UUID uuid: clonedMap.keySet()){

                    Ticket.currentTickets.put(uuid, clonedMap.get(uuid));
                    Ticket ticket = Ticket.currentTickets.get(uuid);
                    if (ticket.priority == 2){
                        plugin.getLogger().warning(ChatColor.YELLOW + "Priority 2 tickets are open!");
                }
            }
        }
    }
}
