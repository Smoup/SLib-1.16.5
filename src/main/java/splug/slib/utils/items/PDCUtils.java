package splug.slib.utils.items;

import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
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
}
