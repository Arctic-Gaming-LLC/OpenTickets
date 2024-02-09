package dev.arcticgaming.opentickets.GUI;

import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.OpenTickets;
import dev.arcticgaming.opentickets.Utils.TicketManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketViewer implements InventoryHolder, Listener {


    public void ticketViewer (Player player){

        Component guiTitle = Component.text()
                .content("Ticket Viewer")
                .color(TextColor.color(OpenTickets.PRIMARY_COLOR))
                .build();

        Inventory ticketViewer = Bukkit.createInventory(player, 54, guiTitle);

        NamespacedKey key = new NamespacedKey(OpenTickets.getPlugin(OpenTickets.class), "TICKET_UUID");

        int slotNumber = 0;
        for (UUID ticketUUID : TicketManager.CURRENT_TICKETS.keySet()) {

            Ticket ticket = TicketManager.CURRENT_TICKETS.get(ticketUUID);
            String permission = "tickets.group." + ticket.getSupportGroup();
            if (player.hasPermission("tickets.admin") || player.isOp() || player.hasPermission(permission)) {
                String currentTicketUUID = ticket.ticketUUID.toString();

                Component displayName = Component.text()
                        .content(ticket.ticketUUID.toString())
                        .color(TextColor.color(OpenTickets.PRIMARY_COLOR))
                        .build();

                List<Component> loreList = new ArrayList<>();

                Component lore1 = Component.text()
                        .content("Player Owner: " + ticket.playerName)
                        .color(TextColor.color(OpenTickets.SECONDARY_COLOR))
                        .build();

                Component lore2 = Component.text()
                        .content("Support Group: " + ticket.supportGroup)
                        .color(TextColor.color(OpenTickets.SECONDARY_COLOR))
                        .build();

                Component lore3 = Component.text()
                        .content("Issue: " + ticket.note)
                        .color(TextColor.color(OpenTickets.SECONDARY_COLOR))
                        .build();


                loreList.add(lore1);
                loreList.add(lore2);
                loreList.add(lore3);

                ItemStack item = new ItemStack(Material.PAPER);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.displayName(displayName);
                itemMeta.lore(loreList);
                itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, currentTicketUUID);
                item.setItemMeta(itemMeta);

                ticketViewer.setItem(slotNumber, item);
                slotNumber++;
            }
        }

        player.openInventory(ticketViewer);
    }
    /**
     * Get the object's inventory.
     *
     * @return The inventory.
     */
    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
