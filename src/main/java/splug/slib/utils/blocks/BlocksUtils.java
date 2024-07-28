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
}
