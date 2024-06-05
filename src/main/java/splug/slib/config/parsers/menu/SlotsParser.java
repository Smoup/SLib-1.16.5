package splug.slib.config.parsers.menu;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.logging.Logger;

@UtilityClass
@SuppressWarnings("unused")
@ToString @EqualsAndHashCode
public class SlotsParser {

    public static Set<Integer> parseSlots(@NonNull ConfigurationSection itemSection, Logger logger) {
        final Set<String> keys = itemSection.getKeys(false);

        if (keys.contains("menu-slot")) {
            return Set.of(itemSection.getInt("menu-slot"));
        }

        if (keys.contains("menu-slots")) {
            final Set<Integer> menuSlots = new HashSet<>();
            final List<String> menuSlotsStrings = itemSection.getStringList("menu-slots");

            for (String slots : menuSlotsStrings) {
                final String[] contents = slots.split("-");
                if (contents.length != 2) {
                    logger.warning(("§f[§cКонфиг§f] [§cМеню§f] §cНеудачная обработка секции §6menu-slots §f|" +
                            " Неверный формат: §6%s §f| путь: %s").formatted(slots, itemSection.getCurrentPath()));
                    continue;
                }

                try {
                    for (int i = Integer.parseInt(contents[0]); i <= Integer.parseInt(contents[1]); i++) {
                        menuSlots.add(i);
                    }
                } catch (NumberFormatException e) {
                    logger.warning(("§f[§cКонфиг§f] [§cМеню§f] §cНеудачная обработка секции §6menu-slots §f|" +
                            " Ошибка числового формата: §6%s §f| ошибка: §c%s §f| путь: %s")
                            .formatted(slots, e.getMessage(), itemSection.getCurrentPath()));
                }
            }
            return menuSlots;
        }

        logger.warning("§f[§cКонфиг§f] [§cМеню§f] §cНеобнаружены секции §6menu-slot §cи §6menu-slots §f|" +
                " Предмет будет отображен в §b0 §fслоте §f| путь: %s".formatted(itemSection.getCurrentPath()));
        return Set.of(0);
    }
}
