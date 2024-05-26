package splug.slib.config.parsers.item;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import splug.slib.utils.items.ItemStackBuilder;

import java.util.logging.Logger;

@UtilityClass
@SuppressWarnings("unused")
@ToString @EqualsAndHashCode
public class ItemStackParser {

    @NonNull
    public static ItemStack parse(@NonNull ConfigurationSection itemSection, Logger logger) {
        final Material material = handleMaterial(logger, itemSection);

        if (material == null) return new ItemStack(Material.ACACIA_BOAT);

        final ItemStackBuilder itemStackBuilder = ItemStackBuilder.of(material);

        EnchantsParser.handleEnchants(logger, itemStackBuilder, itemSection);
        ItemFlagParser.handleItemFlags(logger, itemStackBuilder, itemSection);
        AttributeModifiersParser.handleAttributeModifiers(logger, itemStackBuilder, itemSection);

        itemStackBuilder
            .lore(itemSection.getStringList("lore"))
            .displayName(itemSection.getString("display-name"))
            .amount(itemSection.getInt("amount", 1))
            .unbreakable(itemSection.getBoolean("unbreakable", false))
            .customModelData(itemSection.getInt("custom-model-data"))
            .localizedName(itemSection.getString("localized-name"));

        return handleOther(itemSection, logger, itemStackBuilder);
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
        logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §cНекорректный тип material §f|" +
                " Используйте значение из org.bukkit.Material §f| путь: %s").formatted(sectionPath));
    }

    private static ItemStack handleOther(ConfigurationSection itemSection, Logger logger,
                                         ItemStackBuilder itemStackBuilder) {
        final ItemStack itemStack = LeatherRGBParser.handleRGB(logger, itemStackBuilder, itemSection);

        if (itemStack.getType().equals(Material.TIPPED_ARROW)) {
            TrippedArrowParser.handleTrippedArrow(logger, itemStack, itemSection);
        }

        if (itemStack.getType().equals(Material.PLAYER_HEAD)) {
            PlayerHeadParser.handlePlayerHead(logger, itemStack, itemSection);
        }

        return itemStack;
    }
}
