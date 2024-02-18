package dev.arcticgaming.opentickets.Utils;

import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.OpenTickets;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TicketUtil {

    public static boolean canViewTicket(Ticket ticket, Player player){

        UUID playerUUID = player.getUniqueId();
        String permission = "tickets.group." + ticket.supportGroup;

        //Regular permission verification
        if(player.hasPermission(permission) || player.hasPermission("tickets.admin") || player.isOp()){
            return true;
        }
        //check if they are ticket owner after
        return playerUUID == ticket.getPlayerUUID() && OpenTickets.getPlugin().getConfig().getBoolean("Owners can view");
    }

    public static final Map<UUID, String> playerViewPreferences = new HashMap<>();

    public static Map<UUID, Ticket> filterTicketsBySupportGroup(Player player) {
        String currentView = playerViewPreferences.getOrDefault(player.getUniqueId(), "default");

        if (currentView.equals("default")) {
            return TicketManager.CURRENT_TICKETS;
        }

        Map<UUID, Ticket> filteredTickets = new HashMap<>();
        for (Map.Entry<UUID, Ticket> entry : TicketManager.CURRENT_TICKETS.entrySet()) {
            if (entry.getValue().supportGroup.equals(currentView)) {
                filteredTickets.put(entry.getKey(), entry.getValue());
            }
        }

        return filteredTickets;
    }

    public static void togglePlayerViewPreference(Player player) {
        String[] supportGroups = TicketManager.SUPPORT_GROUPS.toArray(new String[0]);
        String currentView = playerViewPreferences.getOrDefault(player.getUniqueId(), "default");

        int currentIndex = currentView.equals("default") ? -1 : java.util.Arrays.asList(supportGroups).indexOf(currentView);
        currentIndex++;

        if (currentIndex >= supportGroups.length) {
            playerViewPreferences.put(player.getUniqueId(), "default");
        } else {
            playerViewPreferences.put(player.getUniqueId(), supportGroups[currentIndex]);
        }
    }

    public static void setPlayerViewPreference(Player player, String supportGroup) {
        playerViewPreferences.put(player.getUniqueId(), supportGroup);
    }
}
