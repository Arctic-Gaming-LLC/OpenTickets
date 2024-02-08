package dev.arcticgaming.opentickets.Utils;

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
        String finalLoc = x + ":" + y + ":" + z + ":" + uuid;

        return finalLoc;
    }

    public static Location stringToLoc(String string){
        String[] parts = string.split(":");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);
        World world = Bukkit.getWorld(parts[3]);

        return new Location(world, x, y, z);

    }
}
