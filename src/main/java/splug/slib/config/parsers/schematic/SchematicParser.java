package splug.slib.config.parsers.schematic;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import splug.slib.schematic.Schematic;

import java.util.*;
import java.util.logging.Logger;

@UtilityClass
@SuppressWarnings("unused")
@ToString @EqualsAndHashCode
public class SchematicParser {

    public static Schematic parseSchematic(@NonNull ConfigurationSection schemSection, Logger logger) {
        final Map<Integer, Material> structure = parseStructure(schemSection, logger);
        if (structure.isEmpty()) {
            return null;
        }

        final List<Integer> blocksList = schemSection.getIntegerList("blocks");
        if (blocksList.isEmpty()) {
            logger.warning(("§f[§cКонфиг§f] [§cSchematicParser§f] §cСписок блоков §6blocks §cпуст " +
                    "§f| путь: %s").formatted(schemSection.getCurrentPath()));
            return null;
        }
        final int[] blocks = new int[blocksList.size()];
        for (int i = 0; i < blocksList.size(); i++) {
            blocks[i] = blocksList.get(i);
        }

        final int xSize = schemSection.getInt("size.x");
        final int zSize = schemSection.getInt("size.z");
        if (xSize == 0 || zSize == 0) {
            logger.warning(("§f[§cКонфиг§f] [§cSchematicParser§f] §cРазмеры §6xz §cне могут равнятся §60" +
                    "§f| путь: %s").formatted(schemSection.getCurrentPath()));
            return null;
        }

        final int xOffset = schemSection.getInt("offset.x");
        final int yOffset = schemSection.getInt("offset.y");
        final int zOffset = schemSection.getInt("offset.z");

        return new Schematic(structure, blocks, xSize, zSize, xOffset, yOffset, zOffset);
    }

    private static Map<Integer, Material> parseStructure(@NonNull ConfigurationSection schemSection, Logger logger) {
        final Map<Integer, Material> out = new HashMap<>();
        final ConfigurationSection structureSection = schemSection.getConfigurationSection("structure");
        if (structureSection == null) {
            logger.warning("§f[§cКонфиг§f] [§cSchematicParser§f] §cНеудачная обработка секции §6structure §f|" +
                    " Секция не найдена §f| путь: %s".formatted(schemSection.getCurrentPath()));
            return out;
        }
        for (final String key : structureSection.getKeys(false)) {
            final Material material = Material.getMaterial(key.toUpperCase());
            if (material == null) {
                logger.warning(("§f[§cКонфиг§f] [§cSchematicParser§f] §cНеизвестный material " +
                        "§6%s §f| путь: %s").formatted(key, schemSection.getCurrentPath()));
                continue;
            }
            out.put(structureSection.getInt(key), material);
        }
        return out;
    }
}
