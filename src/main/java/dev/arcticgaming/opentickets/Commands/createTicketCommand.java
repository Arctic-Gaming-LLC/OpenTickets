package dev.arcticgaming.opentickets.Commands;

import dev.arcticgaming.opentickets.Utils.TicketManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class createTicketCommand implements CommandExecutor {


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

        Player player = (Player) sender;

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : args) {
            stringBuilder.append(s).append(" ");
        }

        String note = stringBuilder.toString();

        try {
            TicketManager.createTicket(player, note);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        return false;
    }
}
