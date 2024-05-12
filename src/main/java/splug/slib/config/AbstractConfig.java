package splug.slib.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@Getter @SuppressWarnings("unused")
@ToString @EqualsAndHashCode
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

        loadMessages();
    }

    public AbstractConfig(T plugin, String pluginName) {
        plugin.saveDefaultConfig();

        this.plugin = plugin;
        this.cfg = plugin.getConfig();
        this.pluginName = pluginName;

        loadMessages();
    }

    private void loadMessages() {
        final ConfigurationSection groupsSection = cfg.getConfigurationSection("messages");

        if (groupsSection == null) return;

        for (final String groupName : groupsSection.getKeys(false)) {
            parseMessagesGroupSection(groupName, groupsSection);
        }
    }

    private void parseMessagesGroupSection(String groupName, ConfigurationSection groupsSection) {
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
        plugin.getLogger().warning("§сConfig warning -> %s".formatted(logInfo));
    }

    public String getMsgByKey(String key) {
        return messages.get(key);
    }
}
