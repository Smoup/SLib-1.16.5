package splug.slib.menu.buttons;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString @SuppressWarnings("unused")
public class CommandButton extends AbstractMenuButton {

    private final Set<String> commands = new HashSet<>();

    public CommandButton(ItemStack itemStack, String... commands) {
        this(itemStack, Set.of(commands));
    }

    public CommandButton(ItemStack itemStack, Set<String> commands) {
        super(itemStack);
        this.commands.addAll(commands);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        for (final String command : commands) {
            Bukkit.dispatchCommand(event.getWhoClicked(), command);
        }
    }
}
