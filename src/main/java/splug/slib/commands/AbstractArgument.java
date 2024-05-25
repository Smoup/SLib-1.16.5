package splug.slib.commands;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.args.ArgumentData;
import splug.slib.commands.args.ExecutableArgument;
import splug.slib.commands.args.HandleArgumentDataException;
import splug.slib.commands.content.ArgData;
import splug.slib.commands.content.ArgumentContent;
import splug.slib.commands.usage.CommandUsageExecutor;

import java.util.*;

@SuppressWarnings("unused")
@Data @AllArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class AbstractArgument<P extends JavaPlugin, T extends ArgumentData> {

    private final P plugin;
    private final int ordinal;

    private final List<ArgumentContent<T>> contents = new ArrayList<>();
    private final Set<AbstractArgument<P, T>> arguments = new HashSet<>();

    private String permission;
    private final CommandUsageExecutor cmdUsage;

    AbstractArgument(P plugin, int ordinal) {
        this.plugin = plugin;
        this.ordinal = ordinal;
        this.cmdUsage = new CommandUsageExecutor(plugin.getName());
    }

    AbstractArgument(P plugin, int ordinal, String pluginName) {
        this.plugin = plugin;
        this.ordinal = ordinal;
        this.cmdUsage = new CommandUsageExecutor(pluginName);
    }

    protected void sendUsageMSG(CommandSender sender) {
        sender.sendMessage(cmdUsage.getUsageMessage());
    }

    protected void addContent(ArgumentContent<T> argumentContent) {
        contents.add(argumentContent);
    }

    protected void addArgument(AbstractArgument<P, T> abstractArgument) {
        arguments.add(abstractArgument);
    }

    protected boolean executeArguments(CommandSender sender, String[] args, T data) {
        if (args.length < ordinal) return false;

        boolean isTargetArgument = contents.isEmpty();

        try {
            if (!isTargetArgument) {
                isTargetArgument = isTargetArgument(sender, args, data);
            }
        } catch (HandleArgumentDataException e) {
            return true;
        }

        if (!isTargetArgument) return false;

        for (final AbstractArgument<P, T> argument : arguments) {
            if (argument.executeArguments(sender, args, data)) {
                return true;
            }
        }

        if (data.isValid(args)) {
            return tryExecuteThis(sender, data);
        } else {
            if (args.length == ordinal) {
                sendUsageMSG(sender);
            }
        }

        return true;
    }

    private boolean isTargetArgument(CommandSender sender, String[] args, T data) {
        for (int i = 0; i < contents.size(); i++) {
            final ArgumentContent<T> content = contents.get(i);
            try {
                content.handleArgumentData(new ArgData<>(sender, args, data, ordinal, (i == contents.size() - 1)));
            } catch (HandleArgumentDataException e) {
                if (e.isSendUsage()) {
                    sendUsageMSG(sender);
                    throw new HandleArgumentDataException();
                }
                continue;
            }
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private boolean tryExecuteThis(CommandSender sender, T data) {
        if (!sender.hasPermission(getPermission())) return true;

        if (this instanceof ExecutableArgument<?>) {
            final ExecutableArgument<T> argument = (ExecutableArgument<T>) this;
            argument.execute(sender, data);
            return true;
        } else {
            return false;
        }
    }


    protected List<String> handleTabComplete(CommandSender sender, String[] args) {
        if (args.length == ordinal) {
            final Set<String> out = handleTabCompleteThis(sender, args);
            return out == null ? null : new ArrayList<>(out);
        }

        return handleCompleteNext(sender, args);
    }

    private Set<String> handleTabCompleteThis(CommandSender sender, String[] args) {
        final Set<String> out = new HashSet<>();

        for (final ArgumentContent<T> content : contents) {
            if (!content.hasPermission(sender)) continue;

            final Set<String> outResult = content.getArgs(args[ordinal - 1]);
            if (outResult == null) return null;

            out.addAll(content.getArgs(args[ordinal - 1]));
        }

        return out;
    }

    private ArrayList<String> handleCompleteNext(CommandSender sender, String[] args) {
        final Set<String> out = new HashSet<>();

        for (final AbstractArgument<P, T> argument : arguments) {
            if (!sender.hasPermission(argument.getPermission())) continue;
            final List<String> outResult = argument.handleTabComplete(sender, args);
            if (outResult == null) return null;
            out.addAll(outResult);
        }

        return new ArrayList<>(out);
    }

    private void log(Object o) {
        Bukkit.getLogger().warning("§f[§6SLib§f] %s".formatted(String.valueOf(o)));
    }
}
