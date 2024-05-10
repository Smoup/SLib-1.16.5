package splug.slib.menu;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@Getter @ToString
@EqualsAndHashCode
public abstract class AbstractMenuButton {

    private final ItemStack itemStack;

    public AbstractMenuButton(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public abstract void onClick(InventoryClickEvent event);
}
