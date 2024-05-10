package splug.slib.config.parsers;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import splug.slib.utils.items.ItemStackBuilder;

import java.util.logging.Logger;

@SuppressWarnings("unused") @ToString
@EqualsAndHashCode
public class ItemStackParser {

    @NonNull
    public static ItemStack parse(@NonNull ConfigurationSection itemSection, Logger logger) {
        final Material material = handleMaterial(logger, itemSection);

        if (material == null) return new ItemStack(Material.ACACIA_BOAT);

        final ItemStackBuilder itemStackBuilder = ItemStackBuilder.of(material);

        handleEnchants(logger, itemStackBuilder, itemSection);
        handleItemFlags(logger, itemStackBuilder, itemSection);
        itemStackBuilder
                .lore(itemSection.getStringList("lore"))
                .displayName(itemSection.getString("display-name"))
                .amount(itemSection.getInt("amount", 1))
                .unbreakable(itemSection.getBoolean("unbreakable", false))
                .customModelData(itemSection.getInt("custom-model-data"))
                .localizedName(itemSection.getString("localized-name"));

        return handleRGB(logger, itemStackBuilder, itemSection);
    }

    private static ItemStack handleRGB(Logger logger, ItemStackBuilder itemStackBuilder, ConfigurationSection itemSection) {
        if (!itemStackBuilder.materialNameContainsString("LEATHER")) return itemStackBuilder.build();

        final String rgbCode = itemSection.getString("rgb");

        if (rgbCode == null) return itemStackBuilder.build();

        final String[] colors = rgbCode.split(", ");

        if (colors.length != 3) {
            logger.warning(
                "§cItemParser warning -> rgb is invalid, pls use correct format | rgb: red, green, blue");

            return itemStackBuilder.build();
        }

        final int red;
        final int green;
        final int blue;

        try {
            red = Integer.parseInt(colors[0]);
            green = Integer.parseInt(colors[1]);
            blue = Integer.parseInt(colors[2]);
        } catch (NumberFormatException e) {
            logger.warning("§cItemParser warning -> NumberFormatException | section -> %s"
                    .formatted(itemSection.getCurrentPath()));
            return itemStackBuilder.build();
        }

        return itemStackBuilder.build(red, green, blue);
    }

    private static Material handleMaterial
            (Logger logger, ConfigurationSection itemSection) {
        final String materialName = itemSection.getString("material");

        if (materialName == null) {
            invalidMaterial(logger, itemSection.getCurrentPath());
            return null;
        }

        final Material material = Material.getMaterial(materialName);

        if (material == null) {
            invalidMaterial(logger, itemSection.getCurrentPath());
            return null;
        }

        return material;
    }

    private static void invalidMaterial
            (Logger logger, String sectionPath) {
        logger.warning(
                "§cItemParser warning -> material is null, it cannot be null | section -> %s"
                        .formatted(sectionPath)
        );
    }

    private static void handleEnchants
            (Logger logger, ItemStackBuilder itemStackBuilder, ConfigurationSection itemSection) {
        final ConfigurationSection enchantsSection = itemSection.getConfigurationSection("enchants");

        if (enchantsSection == null) return;

        for (final String enchantName : enchantsSection.getKeys(false)) {
            final Enchantment enchant = Enchantment.getByName(enchantName);

            if (enchant == null) {
                logger.warning("§cItemParser warning -> enchantment is undefined | section -> %s"
                        .formatted(enchantsSection.getCurrentPath()));
                continue;
            }

            final int enchantLevel = enchantsSection.getInt(enchantName);

            itemStackBuilder.enchant(enchant, enchantLevel);
        }
    }

    private static void handleItemFlags
            (Logger logger, ItemStackBuilder itemStackBuilder, ConfigurationSection itemSection) {
        final ConfigurationSection itemFlagsSection = itemSection.getConfigurationSection("item-flags");

        if (itemFlagsSection == null) return;

        for (final String itemFlagName : itemFlagsSection.getKeys(false)) {
            final ItemFlag itemFlag;

            try {
                itemFlag = ItemFlag.valueOf(itemFlagName);
            } catch (IllegalArgumentException e) {
                logger.warning("§cItemParser warning -> itemflag is undefined | section -> %s"
                        .formatted(itemFlagsSection.getCurrentPath()));
                continue;
            }

           itemStackBuilder.itemFlags(itemFlag);
        }
    }
}
