package splug.slib.listeners;

import lombok.Data;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Data
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
