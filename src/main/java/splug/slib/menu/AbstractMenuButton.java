package splug.slib.menu;

import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import splug.slib.SJavaPlugin;

@Getter
public abstract class AbstractMenuButton<T extends SJavaPlugin> {

    private final T plugin;
    private final ItemStack itemStack;

    public AbstractMenuButton(T plugin, ItemStack itemStack) {
        this.plugin = plugin;
        this.itemStack = itemStack;
    }

    public abstract void onClick(InventoryClickEvent event);
}
