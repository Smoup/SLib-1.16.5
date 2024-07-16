package splug.slib.config.parsers.bossbar.bukkit;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@SuppressWarnings("unused")
@UtilityClass
public class BukkitBossBarParser {

    @Nullable
    public static BukkitBossBarSettings parse(@NonNull ConfigurationSection section, Logger logger) {
        final BarColor barColor = parseBarColor(section, logger);
        final BarStyle barStyle = parseBarStyle(section, logger);
        final Set<BarFlag> barFlags = parseBarFlags(section, logger);
        if (barColor == null || barStyle == null) return null;
        return new BukkitBossBarSettings(barColor, barStyle, barFlags);
    }

    @Nullable
    private static BarColor parseBarColor(ConfigurationSection section, Logger logger) {
        final String colorKey = section.getString("color");
        if (colorKey == null) {
            logger.warning(("§f[§6Config§f] [§6BossBarParser§f] §cНе обнаружено значение §6color " +
                    "§f| Добавьте его | путь:§6%s").formatted(section.getCurrentPath()));
        } else {
            try {
                return BarColor.valueOf(colorKey);
            } catch (IllegalArgumentException e) {
                logger.warning("§f[§6Config§f] [§6BossBarParser§f] §cНе существует BarColor §6%s".formatted(colorKey) +
                        " §f | Используйте значение из §6org.bukkit.boss.BarColor §f| путь:§6%s".formatted(section.getCurrentPath()));
            }
        }
        return null;
    }

    @Nullable
    private static BarStyle parseBarStyle(ConfigurationSection section, Logger logger) {
        final String styleKey = section.getString("style");
        if (styleKey == null) {
            logger.warning(("§f[§6Config§f] [§6BossBarParser§f] §cНе обнаружено значение §6style " +
                    "§f| Добавьте его | путь:§6%s").formatted(section.getCurrentPath()));
        } else {
            try {
                return BarStyle.valueOf(styleKey);
            } catch (IllegalArgumentException e) {
                logger.warning("§f[§6Config§f] [§6BossBarParser§f] §cНе существует BarStyle §6%s".formatted(styleKey) +
                        " §f | Используйте значение из §6org.bukkit.boss.BarStyle §f| путь:§6%s".formatted(section.getCurrentPath()));
            }
        }
        return null;
    }

    private static Set<BarFlag> parseBarFlags(ConfigurationSection section, Logger logger) {
        final Set<BarFlag> out = new HashSet<>();
        final List<String> barFlagsKeys = section.getStringList("flags");
        for (final String flagKey : barFlagsKeys) {
            try {
                out.add(BarFlag.valueOf(flagKey));
            } catch (IllegalArgumentException e) {
                logger.warning("§f[§6Config§f] [§6BossBarParser§f] §cНе существует BarFlag §6%s".formatted(flagKey) +
                        " §f | Используйте значение из §6org.bukkit.boss.BarFlag §f| путь:§6%s".formatted(section.getCurrentPath()));
            }
        }
        return out;
    }
}
