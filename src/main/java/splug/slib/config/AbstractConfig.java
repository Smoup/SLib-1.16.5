package splug.slib.config;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import splug.slib.SJavaPlugin;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class AbstractConfig<T extends SJavaPlugin> {

    private final T plugin;
    private final FileConfiguration cfg;

    private final Map<String, String> messages = new HashMap<>();

    public AbstractConfig(T plugin) {
        plugin.saveDefaultConfig();

        this.plugin = plugin;
        this.cfg = plugin.getConfig();

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
            String key = groupName + "." + messageKey;

            messages.put(key, messagesGroup.getString(messageKey));
        }
    }
}
