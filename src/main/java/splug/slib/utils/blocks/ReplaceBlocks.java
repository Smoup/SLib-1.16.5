package splug.slib.utils.blocks;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;

@UtilityClass @SuppressWarnings("unused")
public class ReplaceBlocks {

    public static void replaceCubeRadius(Location center, int radius, Material from, Material to) {
        for (int y = (int) (center.getY() - radius); y < center.getY() + radius; y++) {
            for (int z = (int) (center.getZ() - radius); z < center.getZ() + radius; z++) {
                for (int x = (int) (center.getX() - radius); x < center.getX() + radius; x++) {
                    final Location loc = new Location(center.getWorld(), x, y, z);
                    if (loc.getBlock().getType().equals(from)) loc.getBlock().setType(to);
                }
            }
        }
    }
}
