package splug.slib.menu.buttons;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@EqualsAndHashCode(callSuper = true)
@ToString @SuppressWarnings("unused")
public class CommandButton extends AbstractMenuButton {

    private final String command;

    public CommandButton(ItemStack itemStack, String command) {
        super(itemStack);
        this.command = command;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        Bukkit.dispatchCommand(event.getWhoClicked(), command);
    }
}
