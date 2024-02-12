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
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class InventoryClickEventListener implements Listener {
    NamespacedKey key = new NamespacedKey(OpenTickets.plugin, "TICKET_UUID");

    private HashMap<UUID, UUID> TICKET_STORED = new HashMap<>();


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
                case GROUP_SELECT -> handleGroupSelectGUI(event, player);
                // Add more cases for other GUI types here
                default -> {}
            }
        }
    }

    private void handleGroupSelectGUI(InventoryClickEvent event, Player player){
        event.setCancelled(true);
        Ticket ticket = TicketManager.CURRENT_TICKETS.get(TICKET_STORED.get(player.getUniqueId()));

        ItemStack item = event.getInventory().getItem(event.getSlot());
        if (item != null){
            String groupName = PlainTextComponentSerializer.plainText().serialize(item.getItemMeta().displayName());

            ticket.setSupportGroup(groupName);
            TicketManager.updateTicket(ticket);
            player.sendMessage("Support Group Updated!");

            player.closeInventory();
            new TicketViewer().ticketViewer(player);
        }
    }

    private void handleTicketOptionsGUI(InventoryClickEvent event, Player player) {
        event.setCancelled(true);
        Ticket ticket = TicketManager.CURRENT_TICKETS.get(TICKET_STORED.get(player.getUniqueId()));
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
                new GroupSelect().groupSelect(player);
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
        UUID ticketUUID = UUID.fromString(Objects.requireNonNull(item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING)));
        Ticket ticket = TicketManager.CURRENT_TICKETS.get(ticketUUID);
        TICKET_STORED.put(player.getUniqueId(), ticketUUID);

        switch (clickType) {
            case LEFT -> {
                player.closeInventory();
                new TicketOptions().ticketOptions(player);
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
