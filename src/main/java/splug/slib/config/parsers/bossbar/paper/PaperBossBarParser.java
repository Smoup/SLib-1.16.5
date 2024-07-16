package splug.slib.config.parsers.bossbar.paper;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@SuppressWarnings("unused")
@UtilityClass
public class PaperBossBarParser {

    @Nullable
    public static PaperBossBarSettings parse(@NonNull ConfigurationSection section, Logger logger) {
        final BossBar.Color barColor = parseBarColor(section, logger);
        final BossBar.Overlay barOverlay = parseBarOverlay(section, logger);
        final Set<BossBar.Flag> barFlags = parseBarFlags(section, logger);
        if (barColor == null || barOverlay == null) return null;
        return new PaperBossBarSettings(barColor, barOverlay, barFlags);
    }

    @Nullable
    private static BossBar.Color parseBarColor(ConfigurationSection section, Logger logger) {
        final String colorKey = section.getString("color");
        if (colorKey == null) {
            logger.warning(("§f[§6Config§f] [§6BossBarParser§f] §cНе обнаружено значение §6color " +
                    "§f| Добавьте его | путь:§6%s").formatted(section.getCurrentPath()));
        } else {
            try {
                return BossBar.Color.valueOf(colorKey);
            } catch (IllegalArgumentException e) {
                logger.warning("§f[§6Config§f] [§6BossBarParser§f] §cНе существует BossBar.Color §6%s".formatted(colorKey) +
                        " §f | Используйте значение из §6net.kyori.adventure.bossbar.BossBar.Color §f| путь:§6%s".formatted(section.getCurrentPath()));
            }
        }
        return null;
    }

    @Nullable
    private static BossBar.Overlay parseBarOverlay(ConfigurationSection section, Logger logger) {
        final String styleKey = section.getString("overlay");
        if (styleKey == null) {
            logger.warning(("§f[§6Config§f] [§6BossBarParser§f] §cНе обнаружено значение §6overlay " +
                    "§f| Добавьте его | путь:§6%s").formatted(section.getCurrentPath()));
        } else {
            try {
                return BossBar.Overlay.valueOf(styleKey);
            } catch (IllegalArgumentException e) {
                logger.warning("§f[§6Config§f] [§6BossBarParser§f] §cНе существует BossBar.Overlay §6%s".formatted(styleKey) +
                        " §f | Используйте значение из §6net.kyori.adventure.bossbar.BossBar.Overlay §f| путь:§6%s".formatted(section.getCurrentPath()));
            }
        }
        return null;
    }

    private static Set<BossBar.Flag> parseBarFlags(ConfigurationSection section, Logger logger) {
        final Set<BossBar.Flag> out = new HashSet<>();
        final List<String> barFlagsKeys = section.getStringList("flags");
        for (final String flagKey : barFlagsKeys) {
            try {
                out.add(BossBar.Flag.valueOf(flagKey));
            } catch (IllegalArgumentException e) {
                logger.warning("§f[§6Config§f] [§6BossBarParser§f] §cНе существует BossBar.Flag §6%s".formatted(flagKey) +
                        " §f | Используйте значение из §6net.kyori.adventure.bossbar.BossBar.Flag §f| путь:§6%s".formatted(section.getCurrentPath()));
            }
        }
        return out;
    }
}
