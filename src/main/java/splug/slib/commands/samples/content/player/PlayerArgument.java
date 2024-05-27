package splug.slib.commands.samples.content.player;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.AbstractArgument;

@ToString
@EqualsAndHashCode(callSuper = true) @SuppressWarnings("unused")
public class PlayerArgument<P extends JavaPlugin, T extends PlayerCommandData> extends AbstractArgument<P, T> {

    public PlayerArgument(P plugin, int ordinal, String permission) {
        super(plugin, ordinal, permission + ".player-list");

        addContent(new PlayerArgumentContent<>(plugin, getPermission()));
    }

    public PlayerArgument(P plugin, int ordinal, String permission, String pluginName) {
        super(plugin, ordinal, permission + ".player-list");

        addContent(new PlayerArgumentContent<>(plugin, getPermission(), pluginName));
    }
}
