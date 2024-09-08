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

    public static void setPlayerTotalExperience(final @NonNull Player player, final int newExp) {
        if (newExp < 0) throw new IllegalArgumentException("Количество опыта у игрока не должно быть меньше 0");

        player.setExp(0);
        player.setLevel(0);
        player.setTotalExperience(0);

        int amount = newExp;
        while (amount > 0) {
            final int expToLevel = getExpAtLevel(player.getLevel());
            amount -= expToLevel;
            if (amount >= 0) {
                player.giveExp(expToLevel);
            } else {
                amount += expToLevel;
                player.giveExp(amount);
                amount = 0;
            }
        }
    }

    public static int getExpAtLevel(final int level) {
        if (level <= 15) {
            return (2 * level) + 7;
        }

        if (level <= 30) {
            return (5 * level) - 38;
        }
        return (9 * level) - 158;
    }

    public static int getMaterialCountInInv(@NonNull Player player, Material material) {
        int count = 0;

        for (final ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType().equals(material)) {
                count += item.getAmount();
            }
        }

        return count;
    }

    public static void removeMaterialCountFromInv(@NonNull Player player, Material material, int count) {
        for (final ItemStack item : player.getInventory().getContents()) {
            if (item == null || !item.getType().equals(material)) continue;

            if (item.getAmount() >= count) {
                item.setAmount(item.getAmount() - count);
                break;
            } else {
                count -= item.getAmount();
                item.setAmount(0);
            }
        }
    }

    public static boolean hasItemInInventory(Player player, ItemStack itemStack) {
        for (final ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.isSimilar(itemStack)) return true;
        }
        return false;
    }
}
