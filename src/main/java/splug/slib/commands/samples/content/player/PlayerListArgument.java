package splug.slib.commands.samples.content.player;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.AbstractArgument;
import splug.slib.commands.usage.CommandUsageExecutor;

@ToString @EqualsAndHashCode(callSuper = true) @SuppressWarnings("unused")
public class PlayerListArgument<P extends JavaPlugin, T extends PlayerData> extends AbstractArgument<P, T> {

    public PlayerListArgument(P plugin, int ordinal, String permission, CommandUsageExecutor cmdUsage) {
        super(plugin, ordinal, permission, cmdUsage);

        addContent(new PlayerListContent<>(plugin, getPermission()));
    }

    public PlayerListArgument(P plugin, int ordinal, String permission, CommandUsageExecutor cmdUsage, String pluginName) {
        super(plugin, ordinal, permission, cmdUsage);

        addContent(new PlayerListContent<>(plugin, getPermission(), pluginName));
    }
}
