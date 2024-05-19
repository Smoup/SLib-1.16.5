package splug.slib.config.parsers;

import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class PotionEffectParser {

    @NonNull
    public static Set<PotionEffect> parse(@NonNull ConfigurationSection itemSection, Logger logger) {
        final ConfigurationSection potionsSection = itemSection.getConfigurationSection("potion-effects");
        if (potionsSection == null) {
            logger.info(("§f[§6Config§f] [§6PotionEffectParser§f] §cНеудачная обработка секции конфига с эффектами §6potion-effects §f|" +
                    " Причина: секция отсутствует | путь: %s")
                    .formatted(itemSection.getCurrentPath()));
            return Set.of();
        }

        return handleSection(potionsSection, logger);
    }

    private static Set<PotionEffect> handleSection(ConfigurationSection potionsSection, Logger logger) {
        final Set<PotionEffect> potionEffects = new HashSet<>();

        for (final String potionTypeName : potionsSection.getKeys(false)) {
            final ConfigurationSection potionSection = potionsSection.getConfigurationSection(potionTypeName);
            if (potionSection == null) continue;

            final PotionEffect potionEffect = handlePotion(potionSection, logger);
            if (potionEffect == null) continue;

            potionEffects.add(potionEffect);
        }

        return potionEffects;
    }

    private static PotionEffect handlePotion(ConfigurationSection potionSection, Logger logger) {
        final PotionEffectType potionEffectType = PotionEffectType.getByName(potionSection.getName());
        if (potionEffectType == null) {
            logger.info(("§f[§6Config§f] [§6PotionEffectParser§f] §cНеизвестный тип эффекта §6%s §f|" +
                    " Используйте тип эффекта из org.bukkit.potion.PotionEffectType | путь: %s")
                    .formatted(potionSection.getName(), potionSection.getCurrentPath()));
            return null;
        }

        final int duration = potionSection.getInt("duration") * 20;
        if (duration == 0) {
            logger.info(("§f[§6Config§f] [§6PotionEffectParser§f] §cДлительность эффекта §6%s §cравна §b0 §f|" +
                    " Используйте значение больше §b0 §f| путь: %s")
                    .formatted(potionSection.getName(), potionSection.getCurrentPath()));
            return null;
        }

        final int amplifier = potionSection.getInt("amplifier");
        if (amplifier == 0) {
            logger.info(("§f[§6Config§f] [§6PotionEffectParser§f] §cУровень эффекта §6%s §cравен §b0 §f|" +
                    " Используйте значение больше §b0 §f| путь: %s")
                    .formatted(potionSection.getName(), potionSection.getCurrentPath()));
            return null;
        }

        final boolean ambient = potionSection.getBoolean("ambient", true);
        final boolean particles = potionSection.getBoolean("particles", true);

        return new PotionEffect(potionEffectType, duration, amplifier - 1, ambient, particles);
    }
}
