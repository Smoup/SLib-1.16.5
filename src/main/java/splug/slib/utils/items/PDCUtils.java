package splug.slib.utils.items;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import splug.slib.utils.strings.JsonUtils;

import java.util.Collection;

@UtilityClass @SuppressWarnings("unused")
public class PDCUtils {

    public static void saveStringList(PersistentDataContainer pdc, NamespacedKey nKey, Collection<String> strings) {
        pdc.set(nKey, PersistentDataType.STRING, JsonUtils.stringsCollectionToString(strings));
    }

    public static Collection<String> getStringList(PersistentDataContainer pdc, NamespacedKey nKey) {
        final String formatedString = pdc.get(nKey, PersistentDataType.STRING);
        return JsonUtils.stringToStringsCollection(formatedString);
    }

    @Nullable
    public static<T> T getData(@NonNull ItemStack item, NamespacedKey nKey, PersistentDataType<?, T> dataType) {
        if (item.getItemMeta() == null) return null;
        return item.getItemMeta().getPersistentDataContainer().get(nKey, dataType);
    }

    @Nullable
    public static<T> T getData(@NonNull ItemStack item, JavaPlugin plugin, String key, PersistentDataType<?, T> dataType) {
        return getData(item, new NamespacedKey(plugin, key), dataType);
    }

    @Nullable
    public static<T> T getData(@NonNull ItemStack item, String namespace, String key, PersistentDataType<?, T> dataType) {
        return getData(item, new NamespacedKey(namespace, key), dataType);
    }
}
