package splug.slib.config;

import lombok.ToString;
import org.bukkit.configuration.ConfigurationSection;

@SuppressWarnings("unused")
@ToString
public class ConfigException extends RuntimeException {
    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(ConfigurationSection section) {
        super(section.getCurrentPath());
    }
}
