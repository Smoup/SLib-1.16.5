package splug.slib.config.parsers.item;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import splug.slib.utils.items.ItemStackBuilder;

import java.util.logging.Logger;

@UtilityClass
@SuppressWarnings("unused")
@ToString @EqualsAndHashCode
public class LeatherRGBParser {

    public static ItemStack handleRGB(Logger logger, ItemStackBuilder itemStackBuilder,
                                      ConfigurationSection itemSection) {
        if (!itemStackBuilder.materialNameContainsString("LEATHER") ||
                !itemStackBuilder.equalsMaterial(Material.POTION, Material.TIPPED_ARROW,
                        Material.SPLASH_POTION, Material.LINGERING_POTION))
            return itemStackBuilder.build();

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
}
