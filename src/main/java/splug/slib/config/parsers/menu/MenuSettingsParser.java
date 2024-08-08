package splug.slib.config.parsers.menu;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.config.parsers.item.ItemStackParser;
import splug.slib.config.parsers.item.exception.ItemStackParserException;
import splug.slib.menu.AbstractMenu;
import splug.slib.menu.buttons.AbstractMenuButton;
import splug.slib.menu.buttons.BackGroundButton;
import splug.slib.menu.buttons.CloseButton;
import splug.slib.menu.buttons.CommandButton;

import java.util.*;

@UtilityClass
@SuppressWarnings("unused")
@ToString @EqualsAndHashCode
public class MenuSettingsParser {

    public static @NonNull MenuSettings parseMenuSetting(@NonNull ConfigurationSection menuSection, JavaPlugin plugin) {
        final int size = menuSection.getInt("size", 54);
        final Component title = Component.text(menuSection.getString("title", ""));
        final MenuSettings menuSettings = new MenuSettings(size, title);

        final ConfigurationSection contentSection = menuSection.getConfigurationSection("content");
        if (contentSection == null) return menuSettings;
        for (final String key : contentSection.getKeys(false)) {
            final ConfigurationSection itemSection = contentSection.getConfigurationSection(key);
            if (itemSection == null) continue;
            final ItemStack itemStack;
            try {itemStack = ItemStackParser.parse(itemSection, plugin);}
            catch (ItemStackParserException e) {continue;}

            final Set<Integer> slots = SlotsParser.parseSlots(itemSection, plugin.getLogger());
            if (itemSection.getBoolean("close-button", false)) {
                for (final Integer slot : slots) {
                    menuSettings.addButton(slot, new CloseButton(itemStack));
                }
                continue;
            }

            final Set<String> commands = new HashSet<>(itemSection.getStringList("click-commands"));
            if (commands.isEmpty()) {
                for (final Integer slot : slots) {
                    menuSettings.addButton(slot, new BackGroundButton(itemStack));
                }
            } else {
                for (final Integer slot : slots) {
                    menuSettings.addButton(slot, new CommandButton(itemStack, commands));
                }
            }
        }

        return menuSettings;
    }

    @Data @EqualsAndHashCode @ToString
    @SuppressWarnings("unused")
    public static class MenuSettings {
        private final int size;
        private final Component title;
        private final Map<Integer, AbstractMenuButton> buttons = new HashMap<>();

        public MenuSettings(int size, Component title) {
            if (size > 54 || size % 9 != 0) {
                log(("§cРазмер меню не может быть больше §b54 §cи должен быть кратен §b9 §f| размер:" +
                        " §b%d §f| размер установлен на максимально возможный §b54").formatted(size));
                size = 54;
            }

            this.size = size;
            this.title = title;
        }

        public void addButton(int slot, AbstractMenuButton button) {
            buttons.put(slot, button);
        }

        public void putButtonsToMenu(AbstractMenu<?> menu) {
            for (final Map.Entry<Integer, AbstractMenuButton> entry : buttons.entrySet()) {
                menu.addButton(entry.getKey(), entry.getValue());
            }
        }

        private void log(Object o) {
            Bukkit.getLogger().info("§f[§6MenuSettings§f] %s".formatted(o.toString()));
        }
    }
}
