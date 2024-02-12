package dev.arcticgaming.opentickets.Commands;

import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.Utils.TicketManager;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RenameTicket {

    public static void renameTicket(Player player, String[] args) {

        boolean isPlayer = true;
        if (player == null){
            isPlayer = false;
        }

        //args length is at least 2 based on the command structure.
        if (args.length < 3) {
            if(isPlayer){
                player.sendMessage("You're missing the ticket or name!");
            }
            return;
        }

        //Create a "string name" replacing spaces
        String newName = IntStream.range(2, args.length)
                .mapToObj(i -> args[i])
                .collect(Collectors.joining("_"));

        if (newName.length() > 40) {
            if(isPlayer){
                player.sendMessage("That name is too long! 40 character limit including spaces!");
            }
            return;
        }

        Ticket ticket = TicketManager.CURRENT_TICKETS.get(UUID.fromString(args[1]));


        if (ticket != null) {
            // If a ticket is found, rename it.
            ticket.setTicketName(newName);
            TicketManager.updateTicket(ticket);
            if (isPlayer){
                player.sendMessage("Ticket was renamed!");
            }
        } else {
            if(isPlayer){
                player.sendMessage("Ticket was not found! That's odd...");
            }
        }
    }
}
