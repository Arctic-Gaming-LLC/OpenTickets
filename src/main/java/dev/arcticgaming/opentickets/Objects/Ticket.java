package dev.arcticgaming.opentickets.Objects;

import dev.arcticgaming.opentickets.Utils.LocUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Ticket {

    @Getter @Setter public UUID ticketUUID;
    @Getter @Setter public UUID playerUUID;
    @Getter @Setter public String playerName;
    @Getter @Setter String location;
    @Getter @Setter public int supportGroup;
    @Getter @Setter public String note;

    public static Map<UUID, Ticket> currentTickets = new HashMap<>();

    public Ticket(Player player, String note) {

        if (player == null) {
            this.location = null;
            this.playerName = null;
            this.playerUUID = null;
            this.note = "this ticket was not created by a player!\n" + note;
        } else {
            this.location = LocUtil.locToString(player.getLocation());
            this.playerUUID = player.getUniqueId();
            this.playerName = player.getName();
            this.note = note;
        }
        this.ticketUUID = UUID.randomUUID();
        this.supportGroup = 0;
    }
}
