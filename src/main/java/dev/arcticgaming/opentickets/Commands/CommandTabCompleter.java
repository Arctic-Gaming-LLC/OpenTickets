package dev.arcticgaming.opentickets.Commands;

import dev.arcticgaming.opentickets.Utils.TicketManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandTabCompleter implements TabCompleter {

    @Override
    @Nullable
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        if (args.length == 1) {
            // Add the sub-commands
            commands.add("add_note");
            commands.add("change_group");
            commands.add("create");
            commands.add("reload");
            commands.add("rename"); // Add rename to the list of commands
            StringUtil.copyPartialMatches(args[0], commands, completions);
        } else if (args.length == 2) {
            // Extend the specific command to include "rename"
            if (args[0].equalsIgnoreCase("add_note") || args[0].equalsIgnoreCase("change_group") || args[0].equalsIgnoreCase("rename")) {
                // For "rename", include both UUIDs and ticket names as options
                TicketManager.CURRENT_TICKETS.keySet().forEach(uuid -> commands.add(uuid.toString()));
                StringUtil.copyPartialMatches(args[1], commands, completions);
            }
        } else if (args.length == 3) {
            // This block might be reserved for future extensions where "rename" might require additional arguments
            if (args[0].equalsIgnoreCase("change_group")) {
                TicketManager.SUPPORT_GROUPS.forEach(group -> commands.add(group));
                StringUtil.copyPartialMatches(args[2], commands, completions);
            }
            // Potentially handle "rename" command's third argument here
        }

        // Ensure completions are unique to avoid duplicates from UUIDs and Names
        return completions.stream().distinct().collect(Collectors.toList());
    }
}
