package dev.arcticgaming.opentickets.Listeners;

import dev.arcticgaming.opentickets.OpenTickets;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.w3c.dom.Text;

public class PlayerLoginEventListener implements Listener {



    @EventHandler
    public static void playerLoginEventListener(PlayerLoginEvent event){

        Player player = event.getPlayer();



        if (player.hasPermission("opentickets.admin")) {

            Component notification = Component.text()
                    .content("==============================================\n").color(TextColor.color(0xffea00))
                    .append(Component.text("\nThere are open support tickets!\n")
                            .color(TextColor.color(0xffaa00)).clickEvent(ClickEvent.suggestCommand("/viewtickets")))
                    .append(Component.text("\n==============================================\n").color(TextColor.color(0xffea00)))
                    .build();


            Runnable message = () -> {
                player.sendMessage(notification);
            };
            Bukkit.getScheduler().runTaskLater(OpenTickets.getPlugin(), message, 20);
        }
    }
}
