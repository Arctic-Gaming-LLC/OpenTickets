package dev.arcticgaming.opentickets.Commands;

import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.Utils.TicketManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ChangeTicketGroupTabCompleter implements TabCompleter {

    /**
     * @param commandSender
     * @param command
     * @param s
     * @param strings
     * @return
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> completions = new ArrayList<>();

        if (strings.length == 1) {
            // Tab suggestions for ticket names
            for (UUID ticketUUID : TicketManager.CURRENT_TICKETS.keySet()) {
                Ticket ticket = TicketManager.CURRENT_TICKETS.get(ticketUUID);
                completions.add(ticket.getTicketName());
            }
        } else if (strings.length == 2) {
            // Tab suggestions for support groups
            Set<String> supportGroups = TicketManager.SUPPORT_GROUPS;
            completions.addAll(supportGroups);
        }

        return completions;
    }
}
