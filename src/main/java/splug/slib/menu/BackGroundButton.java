package splug.slib.menu;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@EqualsAndHashCode(callSuper = true)
@ToString @SuppressWarnings("unused")
public class BackGroundButton extends AbstractMenuButton {

    public BackGroundButton(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
