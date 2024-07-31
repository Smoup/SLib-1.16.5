package splug.slib.utils.cache;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
@ToString @EqualsAndHashCode(callSuper = false)
public class CacheMap<K ,V> extends LinkedHashMap<K,V> {

    private final int size;

    public CacheMap(int size) {
        this.size = size;
    }

    public CacheMap(Map<? extends K, ? extends V> map, int size) {
        super(map);
        this.size = size;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > size;
    }
}
