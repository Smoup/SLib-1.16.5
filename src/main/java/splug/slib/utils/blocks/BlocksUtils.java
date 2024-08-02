package splug.slib.utils.blocks;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

@UtilityClass @SuppressWarnings("unused")
public class BlocksUtils {
    public static Set<Block> getNearbyBlocks(Location center, int radius) {
        final Set<Block> blocks = new HashSet<>();
        for (double y = (center.getY() - radius); y <= center.getY() + radius; y++) {
            for (double z = (center.getZ() - radius); z <= center.getZ() + radius; z++) {
                for (double x = (center.getX() - radius); x <= center.getX() + radius; x++) {
                    final Location loc = new Location(center.getWorld(), x, y, z);
                    blocks.add(loc.getBlock());
                }
            }
        }
        return blocks;
    }

    public static Set<Location> getCubeEdges(Location center, int radius) {
        final Set<Location> edges = new HashSet<>();
        final int cx = center.getBlockX();
        final int cy = center.getBlockY();
        final int cz = center.getBlockZ();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if ((Math.abs(x) == radius && Math.abs(y) == radius) ||
                            (Math.abs(x) == radius && Math.abs(z) == radius) ||
                            (Math.abs(y) == radius && Math.abs(z) == radius)) {
                        edges.add(new Location(center.getWorld(), cx + x, cy + y, cz + z));
                    }
                }
            }
        }

        return edges;
    }
}
