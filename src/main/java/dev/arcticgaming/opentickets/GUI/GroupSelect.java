package dev.arcticgaming.opentickets.GUI;

import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.OpenTickets;
import dev.arcticgaming.opentickets.Utils.TicketManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GroupSelect implements InventoryHolder, Listener {



    public void groupSelect(Player player) {
        Component guiTitle = Component.text()
                .content("Select Group")
                .color(TextColor.color(OpenTickets.PRIMARY_COLOR))
                .build();

        Inventory groupSelect = Bukkit.createInventory(player, 9, guiTitle);

        int slot = 0;
        for (String group : TicketManager.SUPPORT_GROUPS) {
            ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
            ItemMeta meta = item.getItemMeta();

            Component name = Component.text()
                    .content(group)
                    .color(TextColor.color(OpenTickets.PRIMARY_COLOR))
                    .build();

            ArrayList<Component> loreList = new ArrayList<>();
            Component lore = Component.text()
                    .content("Select this support group!")
                    .color(TextColor.color(OpenTickets.SECONDARY_COLOR))
                    .build();

            meta.displayName(name);
            loreList.add(lore);
            meta.lore(loreList);
            item.setItemMeta(meta);

            groupSelect.setItem(slot, item);
            slot++;
        }

        player.openInventory(groupSelect);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
