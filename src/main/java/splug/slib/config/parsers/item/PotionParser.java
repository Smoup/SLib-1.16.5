package splug.slib.config.parsers.item;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.util.Set;
import java.util.logging.Logger;

public class PotionParser {

    public static void handlePotion
            (Logger logger, ItemStack itemStack, ConfigurationSection itemSection) {
        final PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
        if (potionMeta == null) return;

        final PotionData potionData = parsePotionData(logger, itemSection.getConfigurationSection("potion-data"));
        if (potionData == null) return;
        potionMeta.setBasePotionData(potionData);

        final Set<PotionEffect> potionEffects = PotionEffectParser.parse(itemSection, logger);
        for (final PotionEffect potionEffect : potionEffects) {
            potionMeta.addCustomEffect(potionEffect, true);
        }
        itemStack.setItemMeta(potionMeta);
    }

    public static PotionData parsePotionData(Logger logger, ConfigurationSection section) {
        if (section == null) return null;

        final String potionTypeKey = section.getString("potion-type");
        final PotionType potionType;
        try {
            potionType = PotionType.valueOf(potionTypeKey);
        } catch (IllegalArgumentException e) {
            logger.warning(("§f[§6Config§f] [§6PotionParser§f] §cНекорректный тип зелья §6%s §f|" +
                    " Используйте значение из org.bukkit.potion.PotionType §f| путь: %s")
                    .formatted(potionTypeKey, section.getCurrentPath()));
            return null;
        }

        final boolean extended = section.getBoolean("extended", false);
        final boolean upgraded = section.getBoolean("upgraded", false);

        if (extended && !potionType.isExtendable()) {
            logger.warning(("§f[§6Config§f] [§6PotionParser§f] §cТип зелья §6%s §cне может иметь значение" +
                    " §6extended: true §f| Используйте значение §6false §f| путь: %s")
                    .formatted(potionTypeKey, section.getCurrentPath()));
            return null;
        }

        if (upgraded && !potionType.isUpgradeable()) {
            logger.warning(("§f[§6Config§f] [§6PotionParser§f] §cТип зелья §6%s §cне может иметь значение" +
                    " §6upgraded: true §f| Используйте значение §6false §f| путь: %s")
                    .formatted(potionTypeKey, section.getCurrentPath()));
            return null;
        }

        return new PotionData(potionType, extended, upgraded);
    }
}
