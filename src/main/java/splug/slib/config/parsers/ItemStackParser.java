package splug.slib.config.parsers;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import splug.slib.utils.items.ItemStackBuilder;

import java.util.HashSet;
import java.util.Set;
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

        final ItemStack itemStack = handleRGB(logger, itemStackBuilder, itemSection);

        if (material.equals(Material.TIPPED_ARROW)) {
            handleTrippedArrow(logger, itemStack, itemSection);
        }

        return itemStack;
    }

    private static void handleTrippedArrow(Logger logger, ItemStack itemStack, ConfigurationSection itemSection) {
        final ConfigurationSection trippedEffects = itemSection.getConfigurationSection("tripped-arrow-effects");
        if (trippedEffects == null) {
            logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §cНе обнаружена секция конфига" +
                    " §6tripped-arrow-effects §f| Стрела не будет иметь эффектов | путь: %s")
                    .formatted(itemSection.getCurrentPath()));
            return;
        }

        final Set<PotionData> potionDataSet = parsePotionData(logger, trippedEffects);

        final PotionMeta meta = (PotionMeta) itemStack.getItemMeta();

        if (meta == null) return;

        for (PotionData potionData : potionDataSet) {
            meta.setBasePotionData(potionData);
        }

        itemStack.setItemMeta(meta);
    }

    private static Set<PotionData> parsePotionData
            (Logger logger, ConfigurationSection trippedEffects) {
        final Set<PotionData> potionDataSet = new HashSet<>();

        for (String potionTypeKey : trippedEffects.getKeys(false)) {
            final PotionData potionData = parsePotionData(logger, trippedEffects, potionTypeKey);

            if (potionData == null) continue;

            potionDataSet.add(potionData);
        }

        return potionDataSet;
    }

    private static PotionData parsePotionData(Logger logger, ConfigurationSection trippedEffects, String potionTypeKey) {
        final PotionType potionType = parsePotionType(logger, trippedEffects, potionTypeKey);

        if (potionType == null) return null;

        final ConfigurationSection potionTypeSection = trippedEffects.getConfigurationSection(potionTypeKey);

        if (potionTypeSection == null) return null;

        final boolean isUpgraded = potionTypeSection.getBoolean("long-duration", false);
        final boolean isExtended = potionTypeSection.getBoolean("second-level", false);

        if (isUpgraded && !potionType.isUpgradeable()) {
            logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §cЭффект стрелы §6%s §cне может иметь более" +
                    " высокую длительность §f| Используйте значение §6slong-duration §fна §6false §f|" +
                    " Проверьте org.bukkit.potion.PotionType | путь: %s")
                    .formatted(potionType.name(), potionTypeSection.getCurrentPath()));
            return null;
        }

        if (isExtended && !potionType.isExtendable()) {
            logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §cЭффект стрелы §6%s §cне может быть улучшен до §bII §cуровня  §f|" +
                    " Используйте значение §6second-level §fна §6false §f|" +
                    " Проверьте org.bukkit.potion.PotionType | путь: %s")
                    .formatted(potionType.name(), potionTypeSection.getCurrentPath()));
            return null;
        }

        return new PotionData(potionType, isExtended, isUpgraded);
    }

    private static PotionType parsePotionType(Logger logger, ConfigurationSection trippedEffects, String potionTypeKey) {
        final PotionType potionType;
        try {
            potionType = PotionType.valueOf(potionTypeKey);
        } catch (IllegalArgumentException e) {
            logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §cНеизвестный тип эффекта §6%s §f|" +
                    " Используйте эффекты из org.bukkit.potion.PotionType | путь: %s")
                    .formatted(potionTypeKey, trippedEffects.getCurrentPath()));
            return null;
        }
        return potionType;
    }

    private static ItemStack handleRGB(Logger logger, ItemStackBuilder itemStackBuilder,
                                       ConfigurationSection itemSection) {
        if (!itemStackBuilder.materialNameContainsString("LEATHER")) return itemStackBuilder.build();

        final String rgbCode = itemSection.getString("rgb");

        if (rgbCode == null) return itemStackBuilder.build();

        final String[] colors = rgbCode.split(", ");

        if (colors.length != 3) {
            logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §cНеверный формат R§aG§9B §6%s §f|" +
                    " Используйте формат -> rgb: §cred§f, §agreen§f, §9blue §f| путь: %s")
                    .formatted(rgbCode, itemSection.getCurrentPath()));

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
            logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §cОшибка при обработке числа §6rgb-кода §f|" +
                    " §6код-ошибки: §f%s §f| путь: %s").formatted(e.getMessage(), itemSection.getCurrentPath()));
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
        logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §cНекорректный тип material §f|" +
                " Используйте значение из org.bukkit.Material §f| путь: %s").formatted(sectionPath));
    }

    private static void handleEnchants
            (Logger logger, ItemStackBuilder itemStackBuilder, ConfigurationSection itemSection) {
        final ConfigurationSection enchantsSection = itemSection.getConfigurationSection("enchants");

        if (enchantsSection == null) return;

        for (final String enchantName : enchantsSection.getKeys(false)) {
            final Enchantment enchant = Enchantment.getByName(enchantName);

            if (enchant == null) {
                logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §cНекорректный тип зачарования §6%s §f|" +
                        " Используйте значение из org.bukkit.enchantments.Enchantment §f| путь: %s")
                        .formatted(enchantName, enchantsSection.getCurrentPath()));
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
                logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §cНекорректный тип itemflag §6%s §f|" +
                        " Используйте значение из org.bukkit.inventory.ItemFlag §f| путь: %s")
                        .formatted(itemFlagName, itemFlagsSection.getCurrentPath()));
                continue;
            }

           itemStackBuilder.itemFlags(itemFlag);
        }
    }
}
