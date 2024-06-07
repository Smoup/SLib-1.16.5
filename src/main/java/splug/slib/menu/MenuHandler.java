package splug.slib.menu;

import lombok.Data;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.AbstractArgument;
import splug.slib.commands.data.CommandData;
import splug.slib.commands.samples.content.string.StringContent;

import java.util.HashMap;
import java.util.Map;

@Data @SuppressWarnings("unused")
public class MenuHandler {

    private final Map<Inventory, AbstractMenu<?>> menusByInventory = new HashMap<>();
    private final Map<String, AbstractMenu<?>> menusByName = new HashMap<>();

    public void addMenu(String menuKey, AbstractMenu<?> menu) {
        menusByInventory.put(menu.getInventory(), menu);
        menusByName.put(menuKey, menu);
    }

    public void handleClick(InventoryClickEvent event) {
        final Inventory inventory = event.getWhoClicked().getOpenInventory().getTopInventory();
        if (!menusByInventory.containsKey(inventory)) return;

        menusByInventory.get(inventory).onClick(event);
    }

    public AbstractMenu<?> getMenu(String menuKey) {
        return menusByName.get(menuKey);
    }

    public AbstractMenu<?> getMenu(Inventory inventory) {
        return menusByInventory.get(inventory);
    }

    public void onDisable() {
        for (Inventory inventory : menusByInventory.keySet()) {
            while (inventory.getViewers().iterator().hasNext()) {
                inventory.getViewers().iterator().next().closeInventory();
            }
        }

        menusByInventory.clear();
        menusByName.clear();
    }

    public <P extends JavaPlugin, T extends CommandData> void handleTabCompleter
            (AbstractArgument<P,T> argument, P plugin, String perm) {
        argument.addContent(new StringContent<>(plugin, perm, menusByName.keySet()));
    }
}
