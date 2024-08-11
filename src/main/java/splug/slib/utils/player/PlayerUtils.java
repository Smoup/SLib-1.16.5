package splug.slib.utils.player;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;

@UtilityClass @SuppressWarnings("unused")
public class PlayerUtils {
    public static void giveOrDropItem(Player player, @NonNull ItemStack itemStack) {
        final PlayerInventory inv = player.getInventory();
        final Map<Integer, ItemStack> notGiven = inv.addItem(itemStack);
        if (notGiven.isEmpty()) return;
        final Location loc = player.getLocation();
        notGiven.values().forEach(item -> loc.getWorld().dropItem(loc, item));
    }
}
