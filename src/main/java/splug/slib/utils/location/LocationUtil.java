package splug.slib.utils.location;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@UtilityClass @SuppressWarnings("unused")
public class LocationUtil {
    public static String toString(@NonNull Location loc) {
        if (loc.getWorld() == null) {
            throw new RuntimeException("Location.getWorld() равен null");
        }
        return "%s_%d_%d_%d".formatted(loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public static Location fromString(String s) {
        final var comps = s.split("_");
        if (comps.length == 4) {
            return new Location(Bukkit.getWorld(comps[0]), Integer.parseInt(comps[1]),
                    Integer.parseInt(comps[2]),Integer.parseInt(comps[3]));
        }

        if (comps.length > 4) {
            return new Location(Bukkit.getWorld(getWorldName(comps)), Integer.parseInt(comps[comps.length - 3]),
                    Integer.parseInt(comps[comps.length - 2]),Integer.parseInt(comps[comps.length - 1]));
        }

        return null;
    }

    private static String getWorldName(String[] strings) {
        final var sb = new StringBuilder();
        for (int i = 0; i < strings.length - 3; i++) {
            sb.append(strings[i]).append("_");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    @Deprecated
    public static Location getCenter(Location loc) {
        return getBlockCenter(loc);
    }

    public static Location getBlockCenter(Location loc) {
        return new Location(loc.getWorld(), loc.getX() + 0.5, loc.getY(), loc.getZ() + 0.5);
    }

    public static Location getCenter(Location loc1, Location loc2) {
        return getCenter(loc1.getWorld(), loc1.getX(), loc1.getY(), loc1.getZ(), loc2.getX(), loc2.getY(), loc2.getZ());
    }

    public static Location getCenter(World world, double x1, double y1, double z1, double x2, double y2, double z2) {
        double centerX = (x1 + x2) / 2;
        double centerY = (y1 + y2) / 2;
        double centerZ = (z1 + z2) / 2;
        return new Location(world, centerX, centerY, centerZ);
    }
}
