package splug.slib.menu;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.menu.buttons.AbstractMenuButton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data @SuppressWarnings("unused")
public abstract class AbstractMenu<T extends JavaPlugin> {

    private final T plugin;
    private final Map<Integer, AbstractMenuButton> buttons = new HashMap<>();
    private final Set<Integer> editableSlots = new HashSet<>();
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
        if (!editableSlots.contains((event.getSlot()))) event.setCancelled(true);

        if (!buttons.containsKey(event.getSlot())) return;

        buttons.get(event.getSlot()).onClick(event);
    }

    public void addButton(int slot, AbstractMenuButton button) {
        if (buttons.containsKey(slot)) {
            log("§cСлот §b%d §cиспользован дважды".formatted(slot));
        }

        buttons.put(slot, button);
    }

    public void addButtons(AbstractMenuButton button, int... slots) {
        for (final int slot : slots) {
            if (buttons.containsKey(slot)) {
                log("§cСлот §b%d §cиспользован дважды".formatted(slot));
            }

            buttons.put(slot, button);
        }
    }

    public void addButtonForced(int slot, AbstractMenuButton button) {
        buttons.put(slot, button);
    }

    public void addButtonsForced(AbstractMenuButton button, int... slots) {
        for (final int slot : slots) {
            buttons.put(slot, button);
        }
    }

    public void addEditableSlot(int slot) {
        editableSlots.add(slot);
    }

    public void openMenu(Player player) {
        player.closeInventory();

        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> player.openInventory(inventory), 1L);
    }

    public void log(String msg) {
        getPlugin().getLogger().warning("§8[§6Меню§8] §8[§r%s§8] §f%s".formatted(title, msg));
    }
}
