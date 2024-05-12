package splug.slib.commands;

import com.google.common.collect.Lists;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.command.CommandSender;
import splug.slib.commands.args.Argument;
import splug.slib.commands.usage.CommandUsageExecutor;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter
@EqualsAndHashCode @ToString @SuppressWarnings("unused")
public abstract class AbstractParameter {
    private final int ordinal;
    private String permission;

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
            if (args.length == ordinal) {
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

    protected abstract void execute(CommandSender sender, String[] args);

    protected final List<String> handleComplete(CommandSender sender, String[] args) {
        if (args.length != ordinal) {
            return handleCompleteNext(sender, args);
        }

        return handleCompleteThis(sender, args);
    }

    private List<String> handleCompleteNext(CommandSender sender, String[] args) {
        if (parameters.isEmpty()) return Collections.emptyList();

        for (final AbstractParameter parameter : parameters) {
            if (!parameter.isTarget(sender, args)) continue;

            return parameter.handleComplete(sender, args);
        }

        return Collections.emptyList();
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


////DEBUG
//    public final boolean isTarget(CommandSender sender, String[] args) {
//        if (args.length == 0 && ordinal == 0) {
//            info("1 -> true args | ordinal == 0");
//            cut();
//            return true;
//        }
//        if (args.length < ordinal) {
//            info("2 -> false length=%s ordinal=%s".formatted(args.length, ordinal));
//            cut();
//            return false;
//        }
//
//        final String targetArg = args[ordinal - 1];
//
//        for (final Argument argument : arguments) {
//            if (!sender.hasPermission(argument.getPermission())) {
//                info("3 -> continue no perm %s".formatted(argument.getParams().toString()));
//                cut();
//                continue;
//            }
//
//            if (targetArg.isEmpty()) {
//                info("4 -> true %s".formatted("targetArg is empty"));
//                cut();
//                return true;
//            }
//
//            if (argument.getParams() == null || argument.getParams().contains(targetArg)) {
//                info("5 -> true nullParams=%s containsTarget=%s(target=%s)".formatted(argument.getParams() == null, (argument.getParams() != null) && argument.getParams().contains(targetArg), targetArg));
//                info("5 -> %s".formatted(argument.toString()));
//                cut();
//                return true;
//            } else {
//                for (String param : argument.getParams()) {
//                    if (param.startsWith(targetArg)) {
//                        info("6 -> true %s".formatted("param.startsWith(targetArg)"));
//                        cut();
//                        return true;
//                    }
//                }
//            }
//        }
//
//        info("7 -> false %s".formatted("END"));
//        cut();
//        return false;
//    }
//
//    private void info(Object o) {
//        SLib.getInstance().getLogger().info(String.valueOf(o));
//    }
//
//    private void cut() {
//        SLib.getInstance().getLogger().info("--------------------------------------------------");
//    }
}
