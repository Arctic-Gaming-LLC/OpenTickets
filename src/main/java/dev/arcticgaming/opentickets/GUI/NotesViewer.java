package dev.arcticgaming.opentickets.GUI;

import dev.arcticgaming.opentickets.Objects.Ticket;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotesViewer {

    public static void openNotesBook(Player player, Ticket ticket) {

        // Ticket information
        HashMap<String, String> notes = ticket.notes;

        // Create a new book
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK); // Ensure you're using the correct Material type for your server version
        BookMeta meta = (BookMeta) book.getItemMeta();

        // Set the title and author of the book for identification (optional, as these don't show up when the book is opened this way)
        meta.setTitle(ticket.ticketName);
        meta.setAuthor(ticket.playerName);

        // Prepare the pages
        List<String> pages = new ArrayList<>();
        for (Map.Entry<String, String> entry : notes.entrySet()) {
            String pageContent = entry.getKey() + " »\n" + entry.getValue();
            pages.add(pageContent);
        }

        // Add pages to the book
        meta.setPages(pages);
        book.setItemMeta(meta);

        // Open the book GUI for the player without giving them the book
        player.openBook(book);
    }
}