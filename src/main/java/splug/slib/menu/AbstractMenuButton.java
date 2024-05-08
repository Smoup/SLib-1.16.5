package splug.slib.menu;

import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@Getter
public abstract class AbstractMenuButton {

    private final ItemStack itemStack;

    public AbstractMenuButton(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public abstract void onClick(InventoryClickEvent event);
}
