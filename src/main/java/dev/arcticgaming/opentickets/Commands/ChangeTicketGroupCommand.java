package dev.arcticgaming.opentickets.Commands;

import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.Utils.TicketManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChangeTicketGroupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 2) {
            return false; // Incorrect command usage
        }

        String ticketName = args[0];
        String newGroup = args[1];

        Ticket ticket = TicketManager.getTicketByName(ticketName);
        if (ticket != null) {
            ticket.setSupportGroup(newGroup);
            TicketManager.updateTicket(ticket);
        } else {
            return false;
        }
        return true;
    }
}

