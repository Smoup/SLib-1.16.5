package splug.slib.utils.strings;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;

@UtilityClass @SuppressWarnings("unused")
public class JsonUtils {
    public static String stringsCollectionToString(Collection<String> strings) {
        return new Gson().toJson(strings);
    }

    public static Collection<String> stringToStringsCollection(String string) {
        final Type collectionType = new TypeToken<Collection<String>>() {}.getType();
        try {
            return new Gson().fromJson(string, collectionType);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
