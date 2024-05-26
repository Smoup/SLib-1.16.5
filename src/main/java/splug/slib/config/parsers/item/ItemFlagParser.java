package splug.slib.config.parsers.item;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import splug.slib.utils.items.ItemStackBuilder;

import java.util.logging.Logger;

@UtilityClass
@SuppressWarnings("unused")
@ToString @EqualsAndHashCode
public class ItemFlagParser {

    public static void handleItemFlags
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
