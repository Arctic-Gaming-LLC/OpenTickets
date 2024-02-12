package dev.arcticgaming.opentickets.Commands;

import dev.arcticgaming.opentickets.OpenTickets;
import dev.arcticgaming.opentickets.Utils.TicketManager;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class CreateTicket {


    public static void createTicket(Player player, String[] args){

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : args) {
            stringBuilder.append(s).append(" ");
        }

        String note = stringBuilder.toString();

        try {
            TicketManager.createTicket(player, note);
        } catch (NullPointerException e){
            OpenTickets.getPlugin().getLogger().log(Level.SEVERE, "CRITICAL ERROR! UNABLE TO CREATE TICKET!\n" +
                    "OpenTickets/Commands/createTicketCommand(line 26)");
            e.printStackTrace();
        }
    }
}
