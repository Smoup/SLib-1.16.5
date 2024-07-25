package splug.slib.schematic;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@SuppressWarnings("unused")
@EqualsAndHashCode @ToString
public class Schematic {
    private final Map<Integer, Material> structure;
    private final int[] blocks;

    private final int xSize;
    private final int ySize;
    private final int zSize;

    private final Vector offsetVector;

    public Schematic(Map<Integer, Material> structure, int[] blocks, int xSize, int zSize, int xOffset, int yOffset, int zOffset) {
        this.structure = structure;
        this.blocks = blocks;
        this.xSize = xSize;
        this.ySize = blocks.length / (xSize * zSize);
        this.zSize = zSize;
        this.offsetVector = new Vector(xOffset * -1, yOffset * -1, zOffset * -1);
        handle();
    }

    private void handle() {
        for (int i = 0; i < blocks.length; i++) {
            if (structure.containsKey(blocks[i])) continue;
            blocks[i] = -1;
        }
    }

    public @NotNull PastedSchematic paste(Location loc) {
        final PastedSchematic pastedSchem = new PastedSchematic();
        final Location targetLocation = loc.clone().add(offsetVector);
        int id = 0;
        for (int y = 0; y < ySize; y++, id++) {
            for (int z = 0; z < zSize; z++, id++) {
                for (int x = 0; x < xSize; x++, id++) {
                    final int structureId = blocks[id];
                    if (structureId == -1) continue;
                    final Location toEdit = targetLocation.clone().add(x, y, z);
                    final Block block = toEdit.getBlock();
                    pastedSchem.addNew(toEdit, block.getType());
                    block.setType(structure.get(structureId));
                }
            }
        }

        return pastedSchem;
    }
}
