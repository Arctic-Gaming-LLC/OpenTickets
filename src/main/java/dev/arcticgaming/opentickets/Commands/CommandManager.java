package dev.arcticgaming.opentickets.Commands;

import dev.arcticgaming.opentickets.GUI.TicketViewer;
import dev.arcticgaming.opentickets.OpenTickets;
import dev.arcticgaming.opentickets.Utils.ReloadConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class CommandManager implements CommandExecutor {

    private enum CommandOutput {
        ADD_NOTE,
        CHANGE_GROUP,
        CREATE,
        RELOAD,
        VIEW_TICKETS,
        RENAME,
        UNKNOWN;

        // Method to convert string command to enum
        public static CommandOutput fromString(String commandStr) {
            try {
                return valueOf(commandStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                return UNKNOWN;
            }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Determine the command action based on args length

        boolean isPlayer = false;
        Player player = null;

        if (sender instanceof Player) {
            player = (Player) sender;
            isPlayer = true;
        }
        CommandOutput action = (args.length > 0) ? CommandOutput.fromString(args[0]) : CommandOutput.VIEW_TICKETS;

        switch (action) {
            case RENAME:
                // Handle rename logic here
                if (isPlayer) {
                    if (player.hasPermission("tickets.rename") || player.hasPermission("tickets.admin") || player.isOp()) {
                        RenameTicket.renameTicket(player, args);
                    } else {
                        player.sendMessage("You don't have permission: tickets.rename");
                    }
                } else {
                    RenameTicket.renameTicket(player, args);
                }
                break;
            case ADD_NOTE:
                // Handle add_note logic here
                if (isPlayer) {
                    if(player.hasPermission("tickets.notes") || player.hasPermission("tickets.admin") || player.isOp()){
                        AddNotes.addNoteToTicket(player, args);
                    } else {
                        player.sendMessage("You don't have permission: tickets.notes");
                    }
                }
                break;
            case CHANGE_GROUP:
                // Handle changegroup logic here
                if (isPlayer) {
                    if (player.hasPermission("tickets.changegroup") || player.hasPermission("tickets.admin") || player.isOp()) {
                        ChangeTicketGroup.changeTicketGroup(player, args);
                    } else {
                        player.sendMessage("You don't have permission: tickets.changegroup");
                    }
                } else {
                    ChangeTicketGroup.changeTicketGroup(null, args);
                }
                break;
            case CREATE:
                // Handle create logic here
                if (isPlayer) {
                    if (player.hasPermission("tickets.create") || player.hasPermission("tickets.admin") || player.isOp()) {
                        CreateTicket.createTicket(player, args);
                    } else {
                        player.sendMessage("You don't have permission: tickets.create");
                    }
                } else {
                    OpenTickets.getPlugin().getLogger().log(Level.WARNING, "[Open Tickets] Console cannot create tickets!");
                }
                break;
            case RELOAD:
                // Handle reload logic here
                if (isPlayer) {
                    if (player.hasPermission("tickets.reload") || player.hasPermission("tickets.admin") || player.isOp()) {
                        ReloadConfig.reloadConfig(player);
                    } else {
                        player.sendMessage("You don't have permission: tickets.reload");
                        return false;
                    }
                }
                ReloadConfig.reloadConfig(null);
                break;
            case VIEW_TICKETS:
                // Open a GUI if sender is a player
                if (isPlayer) {
                    if (player.hasPermission("tickets.view") || player.hasPermission("tickets.admin") || player.isOp()){
                        new TicketViewer().ticketViewer(player);
                    }
                } else {
                    OpenTickets.getPlugin().getLogger().log(Level.WARNING, "[Open Tickets] Console cannot view tickets!");
                }

                break;
            case UNKNOWN:
            default:
                if (isPlayer) {
                    if (player.hasPermission("tickets.view") || player.hasPermission("tickets.admin") || player.isOp()){
                        new TicketViewer().ticketViewer(player);
                    }
                }
                break;
        }
        return true;
    }
}
