package splug.slib.config;

import lombok.Data;
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
        if (messagesSection == null) return;

        String path = messagesSection.getCurrentPath();

        if (path == null) return;

        path = path.replace("messages.", "");

        for (final String component : messagesSection.getKeys(false)) {
            if (messagesSection.isConfigurationSection(component)) {
                parseMessages(messagesSection.getConfigurationSection(component));
            } else {
                final String message = messagesSection.getString(component);
                if (message == null) continue;

                final String pluginFormat = "§8[§6%s§8]".formatted(pluginName);
                messages.put("%s.%s".formatted(path, component), message.replace("%plugin%", pluginFormat));
            }
        }
    }

    private void loadMessages1() {
        final ConfigurationSection groupsSection = cfg.getConfigurationSection("messages");

        if (groupsSection == null) return;

        for (final String groupName : groupsSection.getKeys(false)) {
            parseMessages(groupName, groupsSection);
        }
    }

    private void parseMessages(String groupName, ConfigurationSection groupsSection) {
        final ConfigurationSection messagesGroup = groupsSection.getConfigurationSection(groupName);

        if (messagesGroup == null) return;

        for (String messageKey : messagesGroup.getKeys(false)) {
            final String key = groupName + "." + messageKey;
            final String message = messagesGroup.getString(messageKey);
            final String pluginFormat = "§8[§6%s§8]".formatted(pluginName);

            if (message == null) continue;

            messages.put(key, message.replace("%plugin%", pluginFormat));
        }

        messages.put("version", plugin.getDescription().getVersion());
    }

    protected void logWarning(String logInfo) {
        plugin.getLogger().warning("§f[§6Config§f] §c%s".formatted(logInfo));
    }

    protected void logWarning(String logInfo, ConfigurationSection section) {
        plugin.getLogger().warning("§f[§6Config§f] §c%s §f| путь: %s"
                .formatted(logInfo, section.getCurrentPath()));
    }

    public String getMsgByKey(String key) {
        return messages.get(key) != null ? messages.get(key)
                : "§f[§6§f] §8Незарегистрированное в конфиге сообщение | ключ: %s".formatted(key);
    }
}
