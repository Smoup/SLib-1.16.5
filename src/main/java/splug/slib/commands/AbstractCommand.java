package splug.slib.commands;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true) @ToString
public abstract class AbstractCommand extends AbstractParameter implements CommandExecutor, TabCompleter {

    public AbstractCommand(String command, JavaPlugin plugin) {
        super(0, plugin.getName());

        registerCommand(command, plugin);
    }

    public AbstractCommand(String command, JavaPlugin plugin, String pluginName) {
        super(0, pluginName);

        registerCommand(command, plugin);
    }

    private void registerCommand(String command, JavaPlugin plugin) {
        final PluginCommand pluginCMD = plugin.getCommand(command);

        if (pluginCMD != null) {
            setPermission(pluginCMD.getPermission());
            pluginCMD.setExecutor(this);
        } else {
            setPermission("%s.null-permission".formatted(plugin.getName()));
            plugin.getLogger().warning("CommandHandler -> pluginCMD(%s) is null, pls register it -> plugin.yml"
                .formatted(command));
        }
    }

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        executeParameter(sender, args);
        return true;
    }

    @Override
    public final List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return handleComplete(sender, args);
    }
}
