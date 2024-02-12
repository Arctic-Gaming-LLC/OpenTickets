package dev.arcticgaming.opentickets.Utils;

import dev.arcticgaming.opentickets.OpenTickets;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ReloadConfig {

    public static void reloadConfig(Player player) {


        OpenTickets.plugin.reloadConfig();

        //updates the colors and styles!
        OpenTickets.PRIMARY_COLOR = TextColor.fromHexString(Objects.requireNonNull(OpenTickets.getPlugin().getConfig().getString("Colors.primary_color")));
        OpenTickets.SECONDARY_COLOR = TextColor.fromHexString(Objects.requireNonNull(OpenTickets.getPlugin().getConfig().getString("Colors.secondary_color")));

        //updates Support Groups!
        OpenTickets.createSupportGroups();

        if (player != null) {
            player.sendMessage("The config has been reloaded!");
        }

    }
}

