package splug.slib.utils.items;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.Objects;

@UtilityClass
@SuppressWarnings("unused")
public class ItemOwner {
    @Getter
    private static final NamespacedKey namespaceKey = new NamespacedKey("owner", "name");

    public static ItemStack set(ItemStack itemStack, String owner) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        final PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(namespaceKey, PersistentDataType.STRING, owner);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static boolean hasOwner(ItemStack itemStack) {
        return Objects.requireNonNull(itemStack.getItemMeta())
                .getPersistentDataContainer().has(namespaceKey, PersistentDataType.STRING);
    }

    @Nullable
    public static String getOwnerName(ItemStack itemStack) {
        if (!hasOwner(itemStack)) return null;
        final ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        final PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.get(namespaceKey, PersistentDataType.STRING);
    }
}
