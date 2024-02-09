package dev.arcticgaming.opentickets.Objects;

import dev.arcticgaming.opentickets.Utils.LocUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Ticket {

    @Getter
    @Setter
    public UUID ticketUUID;
    @Getter
    @Setter
    public String ticketName;
    @Getter
    @Setter
    public UUID playerUUID;
    @Getter
    @Setter
    public String playerName;
    @Getter
    @Setter
    String location;
    @Getter
    @Setter
    public String supportGroup;
    @Getter
    @Setter
    public String note;

    public Ticket(Player player, String note) {

        this.ticketUUID = UUID.randomUUID();
        this.supportGroup = "default";
        this.location = LocUtil.locToString(player.getLocation());
        this.playerUUID = player.getUniqueId();
        this.playerName = player.getName();
        this.note = note;
        this.ticketName = "Ticket-" + player.getName();
    }

    public Ticket(UUID uuid, String ticketName, UUID playerUUID, String playerName, String location, String supportGroup, String note) {
        this.ticketUUID = uuid;
        this.ticketName = ticketName;
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.location = location;
        this.supportGroup = supportGroup;
        this.note = note;
    }
}
