package splug.slib.config.parsers.item;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@UtilityClass
@SuppressWarnings("unused")
@ToString @EqualsAndHashCode
public class TrippedArrowParser {

    public static void handleTrippedArrow(Logger logger, ItemStack itemStack, ConfigurationSection itemSection) {
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
}
