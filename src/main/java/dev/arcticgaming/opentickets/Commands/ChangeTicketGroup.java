package dev.arcticgaming.opentickets.Commands;

import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.OpenTickets;
import dev.arcticgaming.opentickets.Utils.TicketManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.logging.Level;

public class ChangeTicketGroup {

    public static void changeTicketGroup(Player player, String[] args) {

        boolean isPlayer = player != null;

        if (args.length != 3) {
            if (isPlayer) {
                player.sendMessage("Usage: /tickets change_group <ticketName> <newGroup>");
            } else {
                OpenTickets.getPlugin().getLogger().log(Level.WARNING, "Usage: /tickets change_group <ticketName> <newGroup>");
            }
            return;
        }

        UUID ticketUUID = UUID.fromString(args[1]);
        String newGroup = args[2];

        // Fetch the ticket by UUID.
        Ticket ticket = TicketManager.CURRENT_TICKETS.get(ticketUUID);
        if (ticket != null) {

            // Update the support group of the ticket
            ticket.setSupportGroup(newGroup);
            TicketManager.updateTicket(ticket);
            if (isPlayer) {
                player.sendMessage("Ticket group changed to " + newGroup + ".");
            } else {
                OpenTickets.getPlugin().getLogger().log(Level.WARNING, "Ticket group changed to " + newGroup + ".");
            }
        } else {
            // Inform the player if the ticket does not exist
            if (isPlayer) {
                player.sendMessage("Ticket not found.");
            } else {
                OpenTickets.getPlugin().getLogger().log(Level.WARNING, "Ticket Not Found");
            }
        }
    }
}

