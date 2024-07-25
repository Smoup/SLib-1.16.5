package splug.slib.schematic;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
@EqualsAndHashCode @ToString
public class PastedSchematic {
    private final Map<Location, Material> oldBlocks = new HashMap<>();

    public void addNew(Location loc, Material material) {
        oldBlocks.put(loc, material);
    }

    public void undoPaste() {
        for (final Map.Entry<Location, Material> entry : oldBlocks.entrySet()) {
            entry.getKey().getBlock().setType(entry.getValue());
        }
    }
}
