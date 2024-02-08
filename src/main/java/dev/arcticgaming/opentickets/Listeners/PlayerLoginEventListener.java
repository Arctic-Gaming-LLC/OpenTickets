package dev.arcticgaming.opentickets.Listeners;

import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.OpenTickets;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Objects;

public class PlayerLoginEventListener implements Listener {



    @EventHandler
    public static void playerLoginEventListener(PlayerLoginEvent event){

        Player player = event.getPlayer();



        if (player.hasPermission("tickets.admin")) {
            if (!Ticket.currentTickets.isEmpty()) {

                TextColor colorPrimary = TextColor.fromHexString(Objects.requireNonNull(OpenTickets.getPlugin().getConfig().get("Colors.primary_color")).toString());
                TextColor colorSecondary = TextColor.fromHexString(Objects.requireNonNull(OpenTickets.getPlugin().getConfig().get("Colors.secondary_color")).toString());


                Component notification = Component.text()
                        .content("==============================================\n").color(TextColor.color(colorPrimary))
                        .append(Component.text("\nThere are open support tickets!\n")
                                .color(TextColor.color(colorSecondary)).clickEvent(ClickEvent.suggestCommand("/viewtickets")))
                        .append(Component.text("\n==============================================\n").color(TextColor.color(colorPrimary)))
                        .build();


                Runnable message = () -> {
                    player.sendMessage(notification);
                };
                Bukkit.getScheduler().runTaskLater(OpenTickets.getPlugin(), message, 20);
            }
        }
    }
}
