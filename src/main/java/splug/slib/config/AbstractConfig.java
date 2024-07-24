package splug.slib.config;

import lombok.Data;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@Data @SuppressWarnings("unused")
public abstract class AbstractConfig<T extends JavaPlugin> {

    private final T plugin;
    private final FileConfiguration cfg;
    private final String pluginName;

    private final Map<String, String> messages = new HashMap<>();

    public AbstractConfig(T plugin) {
        plugin.saveDefaultConfig();

        this.plugin = plugin;
        this.cfg = plugin.getConfig();
        this.pluginName = plugin.getName();

        parseMessages(cfg.getConfigurationSection("messages"));
    }

    public AbstractConfig(T plugin, String pluginName) {
        plugin.saveDefaultConfig();

        this.plugin = plugin;
        this.cfg = plugin.getConfig();
        this.pluginName = pluginName;

        parseMessages(cfg.getConfigurationSection("messages"));
    }


    private void parseMessages(ConfigurationSection messagesSection) {
        messages.put("version", plugin.getDescription().getVersion());

        if (messagesSection == null) return;

        String path = messagesSection.getCurrentPath();

        if (path == null) return;

        path = path.replace("messages.", "");

        final String pluginFormat = "§8[§6%s§8]".formatted(pluginName);

        for (final String component : messagesSection.getKeys(false)) {
            if (messagesSection.isConfigurationSection(component)) {
                parseMessages(messagesSection.getConfigurationSection(component));
            } else {
                final String message = messagesSection.getString(component);
                if (message == null) continue;

                messages.put("%s.%s".formatted(path, component), message.replace("%plugin%", pluginFormat));
            }
        }
    }

    protected void log(String logInfo) {
        plugin.getLogger().warning("§f[§6Конфиг§f] §c%s".formatted(logInfo));
    }

    protected void log(String logInfo, ConfigurationSection section) {
        plugin.getLogger().warning("§f[§6Конфиг§f] §c%s §f| путь: %s"
                .formatted(logInfo, section.getCurrentPath()));
    }

    public String getMsgByKey(String key) {
        return messages.get(key) != null ? messages.get(key)
                : "§8[§6%s§8] §cНезарегистрированное в конфиге сообщение §f| ключ: %s".formatted(pluginName, key);
    }

    public Component getCompMsgByKey(String key) {
        return Component.text(messages.get(key) != null ? messages.get(key)
                : "§8[§6%s§8] §cНезарегистрированное в конфиге сообщение §f| ключ: %s".formatted(pluginName, key));
    }
}
