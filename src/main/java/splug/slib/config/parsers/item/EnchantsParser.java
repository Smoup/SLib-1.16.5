package splug.slib.config.parsers.item;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import splug.slib.utils.items.ItemStackBuilder;

import java.util.logging.Logger;

@UtilityClass
@SuppressWarnings("unused")
@ToString @EqualsAndHashCode
public class EnchantsParser {

    public static void handleEnchants
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

            if (enchantName.equalsIgnoreCase("luck")) {
                itemStackBuilder.enchant(Enchantment.LUCK, enchantLevel);
            } else {
                itemStackBuilder.enchant(enchant, enchantLevel);
            }
        }
    }
}
