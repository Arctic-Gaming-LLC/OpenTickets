package dev.arcticgaming.opentickets.Listeners;

import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.OpenTickets;
import dev.arcticgaming.opentickets.Utils.TicketManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Objects;
import java.util.UUID;

public class PlayerLoginEventListener implements Listener {

    @EventHandler
    public static void playerLoginEventListener(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        boolean isViewable = false;
        if (OpenTickets.getPlugin().getConfig().getBoolean("Ticket Notifications")){
            if (!TicketManager.CURRENT_TICKETS.isEmpty()) {
                for (UUID ticketUUID : TicketManager.CURRENT_TICKETS.keySet()) {
                    Ticket ticket = TicketManager.CURRENT_TICKETS.get(ticketUUID);
                    String permission = "tickets.group." + ticket.getSupportGroup();

                    if (player.hasPermission(permission)) {
                        isViewable = true;
                        break; // Exit the loop once a matching ticket is found
                    }
                }

                if (isViewable || player.hasPermission("tickets.admin") || player.isOp()) {
                    // Player has permission to view tickets
                    TextColor colorPrimary = TextColor.fromHexString(Objects.requireNonNull(OpenTickets.getPlugin().getConfig().get("Colors.primary_color")).toString());
                    TextColor colorSecondary = TextColor.fromHexString(Objects.requireNonNull(OpenTickets.getPlugin().getConfig().get("Colors.secondary_color")).toString());

                    Component notification = Component.text()
                            .content("==============================================\n").color(colorPrimary)
                            .append(Component.text("\nThere are open support tickets!\n")
                                    .color(colorSecondary).clickEvent(ClickEvent.suggestCommand("/viewtickets")))
                            .append(Component.text("\n==============================================\n").color(colorPrimary))
                            .build();

                    Runnable message = () -> {
                        player.sendMessage(notification);
                    };
                    Bukkit.getScheduler().runTaskLater(OpenTickets.getPlugin(), message, 20);
                }
            }
        }
    }
}
