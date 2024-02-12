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
            StringUtil.copyPartialMatches(args[0], commands, completions);
        } else if (args.length == 2) {
            // Specific for add_note and change_group which require UUID
            if (args[0].equalsIgnoreCase("add_note") || args[0].equalsIgnoreCase("change_group")) {
                TicketManager.CURRENT_TICKETS.keySet().forEach(uuid -> commands.add(uuid.toString()));
                StringUtil.copyPartialMatches(args[1], commands, completions);
            }
        } else if (args.length == 3) {
            // Specific for change_group which requires a support group name after the UUID
            if (args[0].equalsIgnoreCase("change_group")) {
                TicketManager.SUPPORT_GROUPS.forEach(group -> commands.add(group));
                StringUtil.copyPartialMatches(args[2], commands, completions);
            }
        }

        return completions;
    }
}
