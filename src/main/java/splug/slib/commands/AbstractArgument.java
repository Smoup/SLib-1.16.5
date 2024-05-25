package splug.slib.commands;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.args.ArgumentData;
import splug.slib.commands.args.ExecutableArgument;
import splug.slib.commands.args.HandleArgumentDataException;
import splug.slib.commands.content.ArgumentContent;
import splug.slib.commands.usage.CommandUsageExecutor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
@Data @AllArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class AbstractArgument<P extends JavaPlugin, T extends ArgumentData> {

    private final P plugin;
    private final int ordinal;

    private final Set<ArgumentContent<T>> contents = new HashSet<>();
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

        if (!isTargetArgument) {
            isTargetArgument = isTargetArgument(sender, args, data);
        }

        if (!isTargetArgument) return false;

        for (final AbstractArgument<P, T> argument : arguments) {
            if (argument.executeArguments(sender, args, data)) {
                return true;
            }
        }

        if (data.isValid(args.length)) {
            return tryExecuteThis(sender, data);
        }

        return true;
    }

    private boolean isTargetArgument(CommandSender sender, String[] args, T data) {
        for (final ArgumentContent<T> content : contents) {
            try {
                content.handleArgumentData(sender, args, data, ordinal);
            } catch (HandleArgumentDataException ignored) {
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
}
