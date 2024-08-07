package splug.slib.menu.buttons;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@EqualsAndHashCode(callSuper = true)
@ToString @SuppressWarnings("unused")
public class CloseButton extends AbstractMenuButton {

    public CloseButton(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        event.getWhoClicked().closeInventory();
    }
}
