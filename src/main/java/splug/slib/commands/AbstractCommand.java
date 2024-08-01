package splug.slib.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.args.ExecutableArgument;
import splug.slib.commands.content.ArgumentHandleData;
import splug.slib.commands.data.CommandData;
import splug.slib.commands.exception.ArgsUpdateException;
import splug.slib.commands.exception.ArgumentUseException;
import splug.slib.commands.usage.CommandUsageExecutor;

import java.util.List;

@EqualsAndHashCode(callSuper = true) @ToString @Getter
public abstract class AbstractCommand<P extends JavaPlugin, T extends CommandData> extends AbstractArgument<P, T>
        implements TabExecutor, ExecutableArgument<T> {

    private final CommandUsageExecutor cmdUsage;

    public AbstractCommand(P plugin, String command) {
        super(plugin, 0);
        this.cmdUsage = new CommandUsageExecutor(plugin.getName());

        registerCommand(command);
    }

    private void registerCommand(String command) {
        final PluginCommand pluginCMD = getPlugin().getCommand(command);

        if (pluginCMD != null) {
            setPermission(pluginCMD.getPermission());
            pluginCMD.setExecutor(this);
        } else {
            setPermission("%s.null-permission".formatted(getPlugin().getName()));
            getPlugin().getLogger().warning(("§f[§6CommandManager§f] §сКоманда §6%s §cне зарегистрирована §f|" +
                    " Зарегистрируйте команду в plugin.yml").formatted(command));
        }
    }

    protected void sendUsageMSG(CommandSender sender) {
        sender.sendMessage(cmdUsage.getUsageMessage());
    }

    protected abstract T getNewData();

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command,
                             @NonNull String label, @NonNull String[] args) {
        try {
            return commandExecute(sender, args, getNewData());
        } catch (ArgumentUseException e) {
            sendUsageMSG(sender);
        } catch (ArgsUpdateException e) {
            final ArgumentHandleData<?> handleData = e.getHandleData();
            return onCommand(handleData.getSender(), command, label, handleData.getArgs());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command,
                                      @NonNull String alias, @NonNull String[] args) {
        return tabComplete(sender, args);
    }

    @Override
    public void execute(CommandSender sender, String[] args, T data) {
        sendUsageMSG(sender);
    }
}
