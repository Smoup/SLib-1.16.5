package splug.slib.menu;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
@ToString @EqualsAndHashCode
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
}
