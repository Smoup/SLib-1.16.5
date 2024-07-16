package splug.slib.utils.items.material;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;

@SuppressWarnings("unused")
@UtilityClass
public class MaterialUtils {
    public static boolean isFood(Material material) {
        return switch (material) {
            case APPLE, BAKED_POTATO, BEEF, BEETROOT, BEETROOT_SOUP, BREAD, CARROT, CHICKEN, CHORUS_FRUIT, COD,
                 COOKED_BEEF, COOKED_CHICKEN, COOKED_COD, COOKED_MUTTON, COOKED_PORKCHOP, COOKED_RABBIT, COOKED_SALMON,
                 COOKIE, DRIED_KELP, ENCHANTED_GOLDEN_APPLE, GOLDEN_APPLE, GOLDEN_CARROT, HONEY_BOTTLE, MELON_SLICE,
                 MUSHROOM_STEW, MUTTON, PORKCHOP, POTATO, PUFFERFISH, PUMPKIN_PIE, RABBIT, RABBIT_STEW, SALMON,
                 SPIDER_EYE, SUSPICIOUS_STEW, SWEET_BERRIES, TROPICAL_FISH -> true;
            default -> false;
        };
    }
}
