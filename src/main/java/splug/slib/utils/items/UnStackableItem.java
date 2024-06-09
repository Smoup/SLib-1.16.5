package splug.slib.utils.items;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

@UtilityClass
@SuppressWarnings("unused")
public class UnStackableItem {
    @Getter
    private static final NamespacedKey namespaceKey = new NamespacedKey("random", "id");

    public static ItemStack create(ItemStack itemStack) {
        return ItemStackBuilder.of(itemStack).unStackable(true).build();
    }

    public static boolean isUnStackable(ItemStack itemStack) {
        return Objects.requireNonNull(itemStack.getItemMeta())
                .getPersistentDataContainer().has(namespaceKey, PersistentDataType.STRING);
    }
}
