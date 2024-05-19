package splug.slib.commands;

import com.google.common.collect.Lists;
import lombok.Data;
import org.bukkit.command.CommandSender;
import splug.slib.SLib;
import splug.slib.commands.args.Argument;
import splug.slib.commands.usage.CommandUsageExecutor;

import java.util.*;

@Data @SuppressWarnings("unused")
public abstract class AbstractParameter {
    private final int ordinal;
    private String permission;
    private boolean isLast = false;
    private final HashMap<Integer, Set<Argument>> lastArgs = new HashMap<>();

    private final Set<Argument> arguments = new HashSet<>();
    private final Set<AbstractParameter> parameters = new HashSet<>();
    private final CommandUsageExecutor cmdUsage;

    protected AbstractParameter(int ordinal, String pluginName) {
        this.ordinal = ordinal;
        this.cmdUsage = new CommandUsageExecutor(pluginName);
    }

    public AbstractParameter(int ordinal, String permission, String pluginName) {
        this.ordinal = ordinal;
        this.permission = permission;
        this.cmdUsage = new CommandUsageExecutor(pluginName);
    }

    public AbstractParameter(int ordinal, String permission, CommandUsageExecutor cmdUsage) {
        this.ordinal = ordinal;
        this.permission = permission;
        this.cmdUsage = cmdUsage;
    }

    protected final void executeParameter(CommandSender sender, String[] args) {
        try {
            if (args.length == ordinal || isLast) {
                execute(sender, args);
                return;
            }

            for (final AbstractParameter parameter : parameters) {
                if (parameter.isTarget(sender, args)) {
                    parameter.executeParameter(sender, args);
                }
            }

        } catch (SCommandException ignored) {
        }
    }

    public abstract void execute(CommandSender sender, String[] args);

    protected final List<String> handleComplete(CommandSender sender, String[] args) {
        if (args.length != ordinal) {
            return handleCompleteNext(sender, args);
        }

        return handleCompleteThis(sender, args);
    }

    private List<String> handleCompleteNext(CommandSender sender, String[] args) {
        if (isLast) {
            return handleLastArgs(sender, args);
        }

        if (parameters.isEmpty()) return Collections.emptyList();

        final Set<String> completeList = new HashSet<>();

        for (final AbstractParameter parameter : parameters) {
            if (!parameter.isTarget(sender, args)) continue;

            final List<String> params = parameter.handleComplete(sender, args);
            if (params == null) return null;
            completeList.addAll(params);
        }

        return List.copyOf(completeList);
    }

    private List<String> handleLastArgs(CommandSender sender, String[] args) {
        if (!lastArgs.containsKey(args.length)) return Collections.emptyList();

        final List<String> completeList = Lists.newArrayList();
        final String targetArg = args[args.length - 1];

        for (Argument arg : lastArgs.get(args.length)) {
            if (!sender.hasPermission(arg.getPermission())) continue;

            if (arg.getParams() == null) return null;

            for (String param : arg.getParams()) {
                if (param.startsWith(targetArg)) completeList.add(param);
            }
        }

        return completeList;
    }

    private void info(Object o) {
        SLib.getInstance().getLogger().info(String.valueOf(o));
    }

    private List<String> handleCompleteThis(CommandSender sender, String[] args) {
        final List<String> completeList = Lists.newArrayList();
        final String targetArg = args[ordinal - 1];

        for (Argument arg : arguments) {
            if (!sender.hasPermission(arg.getPermission())) continue;

            if (arg.getParams() == null) return null;

            for (String param : arg.getParams()) {

                if (param.startsWith(targetArg)) completeList.add(param);
            }
        }

        return completeList;
    }

    public final boolean isTarget(CommandSender sender, String[] args) {
        if (args.length == 0 && ordinal == 0) return true;
        if (args.length < ordinal) return false;

        final String targetArg = args[ordinal - 1];

        for (final Argument argument : arguments) {
            if (!sender.hasPermission(argument.getPermission())) continue;

            if (targetArg.isEmpty()) return true;

            if (argument.getParams() == null || argument.getParams().contains(targetArg)) {
                return true;
            } else {
                for (String param : argument.getParams()) {
                    if (param.startsWith(targetArg)) return true;
                }
            }
        }

        return false;
    }

    public final void addArgument(Argument argument) {
        arguments.add(argument);
    }

    public final void addParameter(AbstractParameter parameter) {
        parameters.add(parameter);
    }

    public final void addLastArgument(int ordinal, Argument argument) {
        final Set<Argument> argumentSet = lastArgs.putIfAbsent(ordinal, Set.of(argument));

        if (argumentSet == null) return;

        argumentSet.add(argument);
    }
}
