package dev.arcticgaming.opentickets.Utils;

import dev.arcticgaming.opentickets.OpenTickets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class LocUtil {

    public static String locToString(Location location) {

        double x = location.x();
        double y = location.y();
        double z = location.z();
        UUID uuid = location.getWorld().getUID();

        return x + ":" + y + ":" + z + ":" + uuid;
    }

    public static Location stringToLoc(String string){
        String[] parts = string.split(":");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);
        World world;
        for (World worlds : Bukkit.getWorlds()) {
            if (UUID.fromString(parts[3]).equals(worlds.getUID())) {
                world = worlds;
                return new Location(world, x, y, z);
            }
        }

        world = Bukkit.getWorld(OpenTickets.getPlugin().getConfig().getString("default world"));
        return new Location(world, x, y, z);
    }
}
