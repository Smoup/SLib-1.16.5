package splug.slib.listeners;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @ToString
@EqualsAndHashCode
public abstract class AbstractListener<T extends JavaPlugin> implements Listener {
    private final T plugin;

    public AbstractListener(T plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("unused")
    public void registerEvents() {
        final PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(this, plugin);
    }
}
