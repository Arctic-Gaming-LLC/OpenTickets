package dev.arcticgaming.opentickets.Commands;

import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.Utils.TicketManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AddNotes {

    public static void addNoteToTicket(Player player, String[] args) {

        if (args.length < 2) {
            player.sendMessage("Usage: /tickets add_note <ticketUUID> <note>");
            return;
        }

        UUID ticketUUID;
        try {
            ticketUUID = UUID.fromString(args[1]);
        } catch (IllegalArgumentException e) {
            player.sendMessage("Invalid ticket UUID.");
            return;
        }

        Ticket ticket = TicketManager.CURRENT_TICKETS.get(ticketUUID);
        if (ticket == null) {
            player.sendMessage("Ticket not found.");
            return;
        }

        // Build the note from args
        StringBuilder note = new StringBuilder();
        for (int i = 2; i < args.length; i++) { // Note args start from index 2
            note.append(args[i]);
            if (i < args.length - 1) {
                note.append(" "); // Add space between words
            }
        }

        // Assuming Ticket has a method to handle note addition directly
        ticket.notes.put(player.getName(), note.toString());
        TicketManager.updateTicket(ticket); // Persist changes

        player.sendMessage("Note added to ticket successfully.");
    }
}
