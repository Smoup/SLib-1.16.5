package splug.slib.menu.buttons;

import lombok.Data;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@Data
public abstract class AbstractMenuButton {

    private final ItemStack itemStack;

    public AbstractMenuButton(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public abstract void onClick(InventoryClickEvent event);
}
