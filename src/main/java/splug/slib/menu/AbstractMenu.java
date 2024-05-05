package splug.slib.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import splug.slib.SJavaPlugin;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
@SuppressWarnings("unused")
public abstract class AbstractMenu<T extends SJavaPlugin> {

    private final T plugin;
    private final Map<Integer, AbstractMenuButton<?>> buttons = new HashMap<>();
    private final String title;

    private Inventory inventory;

    public AbstractMenu(T plugin, int size, String title) {
        this.plugin = plugin;
        this.title = title;

        setInventory(Bukkit.createInventory(null, size, title));
    }

    public void fill() {
        buttons.forEach((slot, button) -> inventory.setItem(slot, button.getItemStack()));
    }

    public void onClick(InventoryClickEvent event) {
        if (!buttons.containsKey(event.getSlot())) return;

        buttons.get(event.getSlot()).onClick(event);
    }

    public void addButton(int slot, AbstractMenuButton<?> button) {
        if (buttons.containsKey(slot)) {
            logWarning("menu(%s) uses the slot(%d) twice".formatted(getTitle(), getInventory().getSize()));
        }

        buttons.put(slot, button);
    }

    public void openMenu(Player player) {
        player.closeInventory();

        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> player.openInventory(inventory), 1L);
    }

    public void logWarning(String msg) {
        getPlugin().getLogger().warning("§cMenu warning -> %s".formatted(msg));
    }
}
