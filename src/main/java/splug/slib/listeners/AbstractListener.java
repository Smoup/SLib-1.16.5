package splug.slib.listeners;

import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import splug.slib.SJavaPlugin;

@Getter
public abstract class AbstractListener<T extends SJavaPlugin> implements Listener {
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
