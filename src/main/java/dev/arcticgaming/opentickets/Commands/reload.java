package dev.arcticgaming.opentickets.Commands;

import dev.arcticgaming.opentickets.OpenTickets;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class reload implements CommandExecutor {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission("tickets.admin") || sender.hasPermission("tickets.reload") || sender.isOp()) {

            OpenTickets.plugin.reloadConfig();

            //updates the colors and styles!
            OpenTickets.PRIMARY_COLOR = TextColor.fromHexString(Objects.requireNonNull(OpenTickets.getPlugin().getConfig().getString("Colors.primary_color")));
            OpenTickets.SECONDARY_COLOR = TextColor.fromHexString(Objects.requireNonNull(OpenTickets.getPlugin().getConfig().getString("Colors.secondary_color")));

            //updates Support Groups!
            OpenTickets.createSupportGroups();

            sender.sendMessage("The config has been reloaded!");
        } else {
            sender.sendMessage("You do not have permission to use this command.");
        }

        return true;
    }
}
