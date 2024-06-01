package splug.slib.utils.cooldown;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;

@EqualsAndHashCode @ToString @SuppressWarnings("unused")
public class CooldownManager<T> {
    private final HashMap<T, Long> cooldownMap = new HashMap<>();
    private final long cooldown;

    public CooldownManager(int seconds) {
        this.cooldown = seconds * 1000L;
    }

    public int cooldown(T key) {
        if (cooldownMap.containsKey(key)) {
            final long delay = cooldownMap.get(key) - System.currentTimeMillis();
            if (delay < cooldown) {
                return (int) delay / 1000;
            }
        }

        cooldownMap.put(key, System.currentTimeMillis());
        return 0;
    }
}
