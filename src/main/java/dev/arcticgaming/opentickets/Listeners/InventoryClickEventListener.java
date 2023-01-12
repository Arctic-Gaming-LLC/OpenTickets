package dev.arcticgaming.opentickets.Listeners;

import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.OpenTickets;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class InventoryClickEventListener implements Listener {

    @EventHandler
    public static void inventoryClickEventListener(InventoryClickEvent event){

        String inventoryName = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        ItemStack item = event.getCurrentItem();
        Player player = Bukkit.getPlayer(event.getWhoClicked().getUniqueId());

        if (inventoryName.contains("Ticket Viewer") && item != null){
            event.setCancelled(true);
            ClickType clickType = event.getClick();

            NamespacedKey key = new NamespacedKey(OpenTickets.plugin, "TICKET_UUID");
            UUID ticketUUID = UUID.fromString(Objects.requireNonNull(item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING)));
            Ticket ticket = Ticket.currentTickets.get(ticketUUID);

            switch (clickType) {
                case LEFT -> {
                    assert player != null;
                    Location location = new Location(Bukkit.getWorld(ticket.getWorld()), ticket.getX(), ticket.getY(), ticket.getZ());
                    player.teleport(location);
                }
                case RIGHT -> {
                    ticket.setPriority(2);
                    try {
                        Ticket.saveTickets();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case SHIFT_RIGHT -> {
                    Ticket.closeTicket(ticket);
                    try {
                        Ticket.saveTickets();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                default -> {
                }
            }
        }
    }
}
