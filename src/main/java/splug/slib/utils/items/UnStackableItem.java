package splug.slib.utils.items;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

@UtilityClass
@SuppressWarnings("unused")
public class UnStackableItem {
    @Getter
    private static final NamespacedKey namespaceKey = new NamespacedKey("random", "id");

    public static ItemStack create(ItemStack itemStack) {
        return ItemStackBuilder.of(itemStack).unStackable(true).build();
    }
}
