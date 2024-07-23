package splug.slib.utils.items;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
@ToString @EqualsAndHashCode
public class PDCBuilder {

    private final ItemStack item;
    private final ItemMeta meta;
    private final PersistentDataContainer pdc;
    private final NamespacedKey key;

    public PDCBuilder(ItemStack item, NamespacedKey key) {
        this.item = item;
        this.meta = item.getItemMeta();
        this.pdc = meta.getPersistentDataContainer();
        this.key = key;
    }

    public static PDCBuilder of(ItemStack item, NamespacedKey key) {
        return new PDCBuilder(item, key);
    }

    public static PDCBuilder of(ItemStack item, JavaPlugin plugin, String key) {
        return new PDCBuilder(item, new NamespacedKey(plugin, key));
    }

    public static PDCBuilder of(ItemStack item, String namespace, String key) {
        return new PDCBuilder(item, new NamespacedKey(namespace, key));
    }

    public PDCBuilder stringData(String string) {
        pdc.set(key, PersistentDataType.STRING, string);
        return this;
    }

    public PDCBuilder byteData(byte b) {
        pdc.set(key, PersistentDataType.BYTE, b);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}
