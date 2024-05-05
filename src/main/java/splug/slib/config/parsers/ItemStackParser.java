package splug.slib.config.parsers;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import splug.slib.utils.items.ItemStackBuilder;

import java.util.logging.Logger;

@SuppressWarnings("unused")
public class ItemStackParser {

    @NonNull
    public static ItemStack parse(@NonNull ConfigurationSection itemSection, Logger logger) {
        final ItemStackBuilder itemStackBuilder = ItemStackBuilder.empty();

        handleMaterial(logger, itemStackBuilder, itemSection);
        handleEnchants(logger, itemStackBuilder, itemSection);
        handleItemFlags(logger, itemStackBuilder, itemSection);

        return itemStackBuilder
                .lore(itemSection.getStringList("lore"))
                .displayName(itemSection.getString("display-name"))
                .amount(itemSection.getInt("amount", 1))
                .unbreakable(itemSection.getBoolean("unbreakable", false))
                .customModelData(itemSection.getInt("custom-model-data"))
                .localizedName(itemSection.getString("localized-name"))
                .build();
    }

    private static void handleMaterial(Logger logger, ItemStackBuilder itemStackBuilder, ConfigurationSection itemSection) {
        final String materialName = itemSection.getString("material");

        if (materialName == null) {
            invalidMaterial(logger, itemStackBuilder, itemSection.getCurrentPath());
            return;
        }

        final Material material = Material.getMaterial(materialName);

        if (material == null) {
            invalidMaterial(logger, itemStackBuilder, itemSection.getCurrentPath());
            return;
        }

        itemStackBuilder.material(material);
    }

    private static void invalidMaterial(Logger logger, ItemStackBuilder itemStackBuilder, String sectionPath) {
        itemStackBuilder.material(Material.BARRIER);
        logger.warning(
                "§cItemParser warning -> material is null, it cannot be null | section -> %s"
                        .formatted(sectionPath)
        );
    }

    private static void handleEnchants(Logger logger, ItemStackBuilder itemStackBuilder, ConfigurationSection itemSection) {
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

    private static void handleItemFlags(Logger logger, ItemStackBuilder itemStackBuilder, ConfigurationSection itemSection) {
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
