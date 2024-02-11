package dev.arcticgaming.opentickets.GUI;

import dev.arcticgaming.opentickets.Objects.Ticket;
import dev.arcticgaming.opentickets.OpenTickets;
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
import java.util.List;

public class TicketOptions implements InventoryHolder, Listener {

    Component guiTitle = Component.text()
            .content("Ticket Controls")
            .color(TextColor.color(OpenTickets.PRIMARY_COLOR))
            .build();
    Component groupControlTitle = Component.text()
            .content("Change Support Group")
            .color(TextColor.color(OpenTickets.PRIMARY_COLOR))
            .build();

    Component closeTicketTitle = Component.text()
            .content("Close Ticket")
            .color(TextColor.color(OpenTickets.PRIMARY_COLOR))
            .build();

    Component teleportToTitle = Component.text()
            .content("Teleport to Location")
            .color(TextColor.color(OpenTickets.PRIMARY_COLOR))
            .build();

    Component addNotesTitle = Component.text()
            .content("Add Notes")
            .color(TextColor.color(OpenTickets.PRIMARY_COLOR))
            .build();

    Component viewNotesTitle = Component.text()
            .content("View Notes")
            .color(TextColor.color(OpenTickets.PRIMARY_COLOR))
            .build();
    
    public void ticketOptions(Player player, Ticket ticket) {

        Component guiTitle = Component.text()
                .content("Ticket Controls :" + ticket.ticketUUID)
                .color(TextColor.color(OpenTickets.PRIMARY_COLOR))
                .build();

        Inventory ticketOptions = Bukkit.createInventory(player, 9, guiTitle);

        // making the teleport item
        ItemStack teleportItem = new ItemStack(Material.ENDER_PEARL, 1);
        ItemMeta teleportMeta = teleportItem.getItemMeta();
        teleportMeta.displayName(teleportToTitle);
        List<Component> telportLoreList = new ArrayList<>();
        Component teleportLore2 = Component.text()
                .content("------------------------------")
                .color(TextColor.color(TextColor.color(0x525252)))
                .build();
        Component teleportLore3 = Component.text()
                .content("Teleport to where the ticket was created")
                .color(TextColor.color(TextColor.color(0x525252)))
                .build();
        telportLoreList.add(teleportLore2);
        telportLoreList.add(teleportLore3);
        teleportMeta.lore(telportLoreList);
        teleportItem.setItemMeta(teleportMeta);

        //making Add Notes Item
        ItemStack notesItem = new ItemStack(Material.PAPER, 1);
        ItemMeta notesMeta = notesItem.getItemMeta();
        notesMeta.displayName(addNotesTitle);
        List<Component> notesLoreList = new ArrayList<>();
        Component notesLore3 = Component.text()
                .content("Add a new entry to ticket notes")
                .color(TextColor.color(TextColor.color(0x525252)))
                .build();
        notesLoreList.add(teleportLore2);
        notesLoreList.add(notesLore3);
        notesMeta.lore(notesLoreList);
        notesItem.setItemMeta(notesMeta);

        //Making View Notes Item
        ItemStack viewItem = new ItemStack(Material.BOOK, 1);
        ItemMeta viewMeta = viewItem.getItemMeta();
        viewMeta.displayName(viewNotesTitle);
        List<Component> viewLoreList = new ArrayList<>();
        Component viewLore3 = Component.text()
                .content("opens a log of ticket notes")
                .color(TextColor.color(TextColor.color(0x525252)))
                .build();
        viewLoreList.add(teleportLore2);
        viewLoreList.add(viewLore3);
        viewMeta.lore(viewLoreList);
        viewItem.setItemMeta(viewMeta);

        //make support group item
        ItemStack supportItem = new ItemStack(Material.IRON_PICKAXE, 1);
        ItemMeta supportMeta = supportItem.getItemMeta();
        supportMeta.displayName(groupControlTitle);
        List<Component> supportLoreList = new ArrayList<>();
        Component supportLore3 = Component.text()
                .content("Assign this ticket to a support group")
                .color(TextColor.color(TextColor.color(0x525252)))
                .build();
        supportLoreList.add(teleportLore2);
        supportLoreList.add(supportLore3);
        supportMeta.lore(supportLoreList);
        supportItem.setItemMeta(supportMeta);

        //making the close item
        ItemStack closeItem = new ItemStack(Material.BARRIER, 1);
        ItemMeta closeMeta = closeItem.getItemMeta();
        closeMeta.displayName(closeTicketTitle);
        List<Component> closeLoreList = new ArrayList<>();
        Component closeLore3 = Component.text()
                .content("Player will be notified their ticket was closed")
                .color(TextColor.color(TextColor.color(0x525252)))
                .build();
        closeLoreList.add(teleportLore2);
        closeLoreList.add(closeLore3);
        closeMeta.lore(closeLoreList);
        closeItem.setItemMeta(closeMeta);

        ticketOptions.setItem(2, teleportItem);
        ticketOptions.setItem(3, notesItem);
        ticketOptions.setItem(4, viewItem);
        ticketOptions.setItem(5, supportItem);
        ticketOptions.setItem(6, closeItem);


        //finally show inventory
        player.openInventory(ticketOptions);
    }



    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
