package dev.arcticgaming.opentickets.Listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginEventListener implements Listener {

    @EventHandler
    public static void playerLoginEventListener(PlayerLoginEvent event){

        Player player = event.getPlayer();

        if (player.hasPermission("opentickets.admin")){

            Component priorityMessage = Component.text()
                    .content("[Open Tickets] Tickets open with Level 2 priority!")
                    .color(TextColor.color(0xf05800))
                    .build();

            player.sendMessage(priorityMessage);
        }


    }
}
