package splug.slib.commands;

import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.args.AbstractArgument;
import splug.slib.commands.args.ArgumentData;
import splug.slib.commands.args.ExecutableArgument;

import java.util.List;

public abstract class AbstractCommand<P extends JavaPlugin, T extends ArgumentData>
        extends AbstractArgument<P, T> implements ExecutableArgument<T>, CommandExecutor, TabCompleter {

    public AbstractCommand(P plugin, String command) {
        super(plugin, 0);

        registerCommand(command);
    }

    private void registerCommand(String command) {
        final PluginCommand pluginCMD = getPlugin().getCommand(command);

        if (pluginCMD != null) {
            setPermission(pluginCMD.getPermission());
            pluginCMD.setExecutor(this);
        } else {
            setPermission("%s.null-permission".formatted(getPlugin().getName()));
            getPlugin().getLogger().warning("§f[§6CommandManager§f] §сКоманда §6%s §cне зарегистрирована §f| Зарегистрируйте команду в plugin.yml"
                    .formatted(command));
        }
    }

    protected abstract T getNewData();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return executeArguments(sender, args, getNewData());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return handleTabComplete(sender, args);
    }
}
