package dev.arcticgaming.opentickets.Listeners;

import dev.arcticgaming.opentickets.GUI.GroupSelect;
import dev.arcticgaming.opentickets.GUI.NotesViewer;
import dev.arcticgaming.opentickets.GUI.TicketOptions;
import dev.arcticgaming.opentickets.GUI.TicketViewer;
import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.OpenTickets;
import dev.arcticgaming.opentickets.Utils.LocUtil;
import dev.arcticgaming.opentickets.Utils.TicketManager;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
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
    public void inventoryClickEventListener(InventoryClickEvent event) {
        String inventoryName = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        if (item != null && player != null) {
            GUIType guiType = determineGUIType(inventoryName);
            switch (guiType) {
                case TICKET_VIEWER -> handleTicketViewerGUI(event, player, item);
                case TICKET_OPTIONS -> handleTicketOptionsGUI(event, player);
                // Add more cases for other GUI types here
                default -> {}
            }
        }
    }
    private void handleTicketOptionsGUI(InventoryClickEvent event, Player player) {
        event.setCancelled(true);
        String inventoryTitle = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        String[] parts = inventoryTitle.split("»");
        Ticket ticket = TicketManager.CURRENT_TICKETS.get(UUID.fromString(parts[1].trim()));
        int slotClicked = event.getSlot();
        switch (slotClicked) {
            case 2:
                player.closeInventory();
                player.teleport(LocUtil.stringToLoc(ticket.getLocation()));
                break;
            case 3:
                player.sendMessage("No Ability to add notes yet!");
                break;
            case 4:
                player.closeInventory();
                new NotesViewer().openNotesBook(player, ticket);
                break;
            case 5:
                player.closeInventory();
                new GroupSelect().groupSelect(player, ticket);
                break;
            case 6:
                player.closeInventory();
                TicketManager.closeTicket(ticket);
                player.openInventory(new TicketViewer().getInventory());
            default:
        }

    }
    private void handleTicketViewerGUI(InventoryClickEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
        ClickType clickType = event.getClick();
        NamespacedKey key = new NamespacedKey(OpenTickets.plugin, "TICKET_UUID");
        UUID ticketUUID = UUID.fromString(Objects.requireNonNull(item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING)));
        Ticket ticket = TicketManager.CURRENT_TICKETS.get(ticketUUID);

        switch (clickType) {
            case LEFT -> {
                player.closeInventory();
                new TicketOptions().ticketOptions(player, ticket);
            }
            case RIGHT -> {
                // Open Notes Viewer directly.
                player.closeInventory();
                new NotesViewer().openNotesBook(player, ticket); // Assuming openNotesBook is static or NotesViewer is properly instantiated
            }
            case SHIFT_RIGHT -> {
                TicketManager.closeTicket(ticket);
                try {
                    TicketManager.saveTickets();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.closeInventory();
                new TicketViewer().ticketViewer(player);
            }
            case SHIFT_LEFT -> {
                player.closeInventory();
                player.teleport(LocUtil.stringToLoc(ticket.getLocation()));
            }
            default -> {}
        }
    }

    private GUIType determineGUIType(String inventoryName) {
        if (inventoryName.contains("Ticket Viewer")) {
            return GUIType.TICKET_VIEWER;
        }
        if (inventoryName.contains("Ticket Controls")) {
            return GUIType.TICKET_OPTIONS;
        }
        if (inventoryName.contains("Select Group")) {
            return GUIType.GROUP_SELECT;
        }
        // Add more checks for other GUI types here
        return GUIType.UNKNOWN;
    }

    private enum GUIType {
        TICKET_VIEWER,
        TICKET_OPTIONS,
        GROUP_SELECT,
        UNKNOWN
    }
}
