package dev.arcticgaming.opentickets.GUI;

import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.OpenTickets;
import dev.arcticgaming.opentickets.Utils.TicketManager;
import dev.arcticgaming.opentickets.Utils.TicketUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TicketViewer implements InventoryHolder, Listener {


    public void ticketViewer (Player player){

        Component guiTitle = Component.text()
                .content("Ticket Viewer")
                .color(TextColor.color(OpenTickets.PRIMARY_COLOR))
                .build();

        Inventory ticketViewer = Bukkit.createInventory(player, 54, guiTitle);
        NamespacedKey key = new NamespacedKey(OpenTickets.getPlugin(OpenTickets.class), "TICKET_UUID");

        //begin support group toggle item
        Component toggleTitle = Component.text()
                .content("Filter View")
                .color(TextColor.color(OpenTickets.PRIMARY_COLOR))
                .build();

        List<Component> supportLoreList = new ArrayList<>();

        Component slore1 = Component.text()
                .content("Current Filter: " + TicketUtil.playerViewPreferences.get(player.getUniqueId()))
                .color(OpenTickets.SECONDARY_COLOR)
                .build();

        Component slore2 = Component.text()
                .content("Left-Click » filter view per group")
                .color(OpenTickets.SECONDARY_COLOR)
                .build();

        Component slore3 = Component.text()
                .content("Right-Click » set to default view")
                .color(OpenTickets.SECONDARY_COLOR)
                .build();

        supportLoreList.add(slore1);
        supportLoreList.add(slore2);
        supportLoreList.add(slore3);

        ItemStack sitem = new ItemStack(Material.HOPPER);
        ItemMeta sitemMeta = sitem.getItemMeta();
        sitemMeta.displayName(toggleTitle);
        sitemMeta.lore(supportLoreList);
        sitem.setItemMeta(sitemMeta);

        ticketViewer.setItem(49, sitem);


        int slotNumber = 0;
        if (!TicketManager.CURRENT_TICKETS.isEmpty()) {

            Map<UUID, Ticket> filteredTickets = TicketUtil.filterTicketsBySupportGroup(player);

            for (UUID ticketUUID : filteredTickets.keySet()) {

                Ticket ticket = filteredTickets.get(ticketUUID);

                if (TicketUtil.canViewTicket(ticket, player)) {
                    String currentTicketUUID = ticket.ticketUUID.toString();

                    Component displayName = Component.text()
                            .content(ticket.ticketName)
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
                            .content("Issue: " + ticket.description)
                            .color(TextColor.color(OpenTickets.SECONDARY_COLOR))
                            .build();

                    Component lore4 = Component.text()
                            .content("Ticket UUID: " + ticket.ticketUUID)
                            .color(TextColor.color(OpenTickets.SECONDARY_COLOR))
                            .build();


                    loreList.add(lore1);
                    loreList.add(lore2);
                    loreList.add(lore3);
                    loreList.add(lore4);

                    ItemStack item = new ItemStack(Material.PAPER);
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.displayName(displayName);
                    itemMeta.lore(loreList);
                    itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, currentTicketUUID);
                    item.setItemMeta(itemMeta);

                    ticketViewer.setItem(slotNumber, item);
                    if (slotNumber < 45) {
                        slotNumber++;
                    } else {
                        break;
                    }
                }
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
