package splug.slib.config.parsers.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.logging.Logger;


@UtilityClass
@SuppressWarnings("unused")
@ToString @EqualsAndHashCode
public class PlayerHeadParser {

    public static void handlePlayerHead(Logger logger, ItemStack itemStack, ConfigurationSection itemSection) {
        final String texture = itemSection.getString("player-head-texture");
        if (texture == null) {
            logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §cНе обнаружено значение конфига" +
                    " §6player-head-texture §f| Голова будет иметь обычную текстуру | путь: %s")
                    .formatted(itemSection.getCurrentPath()));
            return;
        }

        final SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        if (skullMeta == null) return;

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §cПри обработке головы произошел пи**ец " +
                    "§f| Голова будет иметь обычную текстуру | путь: %s | ошибка: §c%s")
                    .formatted(itemSection.getCurrentPath(), exception.getMessage()));
        }

        itemStack.setItemMeta(skullMeta);
    }
}
