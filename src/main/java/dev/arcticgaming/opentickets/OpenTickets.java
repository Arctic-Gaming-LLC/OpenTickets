package dev.arcticgaming.opentickets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.arcticgaming.opentickets.Commands.createTicketCommand;
import dev.arcticgaming.opentickets.Commands.reload;
import dev.arcticgaming.opentickets.Commands.viewTicketsCommand;
import dev.arcticgaming.opentickets.Listeners.InventoryClickEventListener;
import dev.arcticgaming.opentickets.Listeners.PlayerLoginEventListener;
import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.Utils.TicketDeserializer;
import dev.arcticgaming.opentickets.Utils.TicketManager;
import dev.arcticgaming.opentickets.Utils.UUIDDeserializer;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public final class OpenTickets extends JavaPlugin implements Listener {

    @Getter public static OpenTickets plugin;

    @Getter @Setter public static TextColor PRIMARY_COLOR;
    @Getter @Setter public static TextColor SECONDARY_COLOR;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        getConfig();
        saveDefaultConfig();

        //Get Config Options for coloration
        PRIMARY_COLOR = TextColor.fromHexString(getConfig().getString("Colors.primary_color"));
        SECONDARY_COLOR = TextColor.fromHexString(getConfig().getString("Colors.secondary_color"));

        //Establish Commands
        Objects.requireNonNull(getCommand("createTicket")).setExecutor(new createTicketCommand());
        Objects.requireNonNull(getCommand("viewTickets")).setExecutor(new viewTicketsCommand());
        Objects.requireNonNull(getCommand("reloadTickets")).setExecutor(new reload());

        //Register Listeners
        Bukkit.getPluginManager().registerEvents(this, this);
        final PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new InventoryClickEventListener(), this);
        pm.registerEvents(new PlayerLoginEventListener(), this);

        //Load and initialize existing tickets
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

        //Updates Support Groups from config
        createSupportGroups();

        Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDDeserializer()).registerTypeAdapter(Ticket.class, new TicketDeserializer()).setPrettyPrinting().create();
        File file = new File(OpenTickets.getPlugin().getDataFolder().getAbsolutePath() + "/tickets.json");

        if (file.exists()) {

            Reader reader = new FileReader(file);

            Type type = new TypeToken<HashMap<UUID, Ticket>>() {
            }.getType();
            HashMap<UUID, Ticket> clonedMap = gson.fromJson(reader, type);

            for (UUID uuid : clonedMap.keySet()) {
                TicketManager.CURRENT_TICKETS.put(uuid, clonedMap.get(uuid));
            }
        }
    }

    public static void createSupportGroups() {

        //Since we'll use this universally - lets just clear it first!
        TicketManager.SUPPORT_GROUPS.clear();

        List<String> customSupportGroups = OpenTickets.getPlugin().getConfig().getStringList("Support Groups");
        for (String group : customSupportGroups) {
            TicketManager.addGroup(group);
        }
    }
}

/*TODO
Implement report cooldowns
 */
