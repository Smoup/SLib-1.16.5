package splug.slib.utils.player;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
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

    public static void setPlayerTotalExperience(@NonNull Player player, int totalExp) {
        player.setTotalExperience(totalExp);

        final int level;
        final int expForNextLevel;
        final float expProgress;

        if (totalExp <= 352) {
            level = (int) (Math.sqrt(totalExp + 9) - 3);
            int expAtLevel = level * level + 6 * level;
            expForNextLevel = 2 * level + 7;
            expProgress = (float) (totalExp - expAtLevel) / expForNextLevel;
        } else if (totalExp <= 1507) {
            level = (int) ((81 / 10.0) + Math.sqrt(2.0 / 5 * (totalExp - 7839.0 / 40)));
            int expAtLevel = (int) (2.5 * level * level - 40.5 * level + 360);
            expForNextLevel = 5 * level - 38;
            expProgress = (float) (totalExp - expAtLevel) / expForNextLevel;
        } else {
            level = (int) ((325 / 18.0) + Math.sqrt(2.0 / 9 * (totalExp - 54215.0 / 72)));
            int expAtLevel = (int) (4.5 * level * level - 162.5 * level + 2220);
            expForNextLevel = 9 * level - 158;
            expProgress = (float) (totalExp - expAtLevel) / expForNextLevel;
        }

        player.setLevel(level);
        player.setExp(expProgress);
    }

    public static int getMaterialCountInInv(@NonNull Player player, Material material) {
        int count = 0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType().equals(material)) {
                count += item.getAmount();
            }
        }

        return count;
    }
}
