package splug.slib;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class SLib extends JavaPlugin {

    @Getter
    private static SLib instance;

    @Override
    public void onEnable() {
        instance = this;
    }
}
