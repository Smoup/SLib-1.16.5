package splug.slib.config;

import org.bukkit.configuration.ConfigurationSection;

@SuppressWarnings("unused")
public class ConfigException extends RuntimeException {
    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(ConfigurationSection section) {
        super(section.getCurrentPath());
    }
}
