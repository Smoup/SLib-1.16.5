package splug.slib.commands;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.args.ExecutableArgument;
import splug.slib.commands.content.ArgumentContent;
import splug.slib.commands.content.ArgumentHandleData;
import splug.slib.commands.data.CommandData;
import splug.slib.commands.exception.ArgumentUseException;
import splug.slib.commands.exception.HandleArgumentDataException;

import java.util.*;
import java.util.stream.Collectors;

@Data @SuppressWarnings("unused") @ToString @EqualsAndHashCode
public abstract class AbstractArgument<P extends JavaPlugin, T extends CommandData> {

    private final P plugin;
    //Место аргумента по порядку начиная с единицы после основной команды (/команда 1.аргумент 2.аргумент...)
    private final int ordinal;

    //Контент текущего аргумента
    private final Set<ArgumentContent<T>> contentSet = new HashSet<>();
    //Аргументы, которые следуют после текущего аргумента
    private final Set<AbstractArgument<P, T>> argumentSet = new HashSet<>();

    private String permission;
    private String noPermissionMessage = "§8[§6%s§8] §cИзвините, но у вас недостаточно прав для этого";

    public void addContent(ArgumentContent<T> argumentContent) {
        contentSet.add(argumentContent);
    }
    public void addArgument(AbstractArgument<P, T> abstractArgument) {
        argumentSet.add(abstractArgument);
    }

    AbstractArgument(P plugin, int ordinal) {
        this(plugin, ordinal, null);
    }

    public AbstractArgument(P plugin, int ordinal, String permission) {
        this(plugin, ordinal, permission, plugin.getName());
    }

    public AbstractArgument(P plugin, int ordinal, String permission, String pluginName) {
        this.plugin = plugin;
        this.ordinal = ordinal;
        this.permission = permission;

        noPermissionMessage = noPermissionMessage.formatted(pluginName);
    }

    //["remove", "sss"]
    protected boolean commandExecute(CommandSender sender, String[] args, T data) {
        if (args.length < ordinal) return false;

        handleContent(sender, args, data);

        if (args.length > ordinal && commandExecuteNext(sender, args, data)) return true;

        if (data.isValid(args)) {
            return commandExecuteThis(sender, args, data);
        }

        return true;
    }

    private void handleContent(CommandSender sender, String[] args, T data) {
        final Iterator<ArgumentContent<T>> contentIterator = contentSet.iterator();
        while (contentIterator.hasNext()) {
            final ArgumentContent<T> content = contentIterator.next();
            try {
                content.handleArgumentData(new ArgumentHandleData<>(sender, args, data, ordinal, contentIterator.hasNext()));
            } catch (HandleArgumentDataException exception) {
                if (exception.isSendUsage()) {
                    throw new ArgumentUseException();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private boolean commandExecuteThis(CommandSender sender, String[] args, T data) {
        try {
            if (this instanceof ExecutableArgument<?>) {
                final ExecutableArgument<T> executableArgument = (ExecutableArgument<T>) this;
                executableArgument.execute(sender, args, data);
            } else {
                return false;
            }
        } catch (ClassCastException exception) {
            plugin.getLogger().warning("§8[§6AbstractArgument§8] §fКак оно так случилася хз | ошибка: §c%s"
                    .formatted(exception.getMessage()));
        }
        return true;
    }

    //DEBUG
    private boolean commandExecuteNext(CommandSender sender, String[] args, T data) {
        for (final AbstractArgument<P, T> argument : argumentSet) {
            log("---------------------------------------");
            for (ArgumentContent<T> tArgumentContent : argument.getContentSet()) {
                log(Arrays.toString(tArgumentContent.getArgs().toArray()));
            }
            boolean isTargetArgument = argument.isTargetArgument(args[ordinal]);
            log(isTargetArgument);
            if (!isTargetArgument) continue;
            if (argument.senderNoPermission(sender)) return true;
            if (argument.commandExecute(sender, args, data)) {
                log("executed");
                return true;
            }
        }
        return false;
    }

//    private boolean commandExecuteNext(CommandSender sender, String[] args, T data) {
//        for (final AbstractArgument<P, T> argument : argumentSet) {
//            if (!argument.isTargetArgument(args[ordinal])) continue;
//            if (argument.senderNoPermission(sender)) return true;
//            if (argument.commandExecute(sender, args, data)) return true;
//        }
//        return false;
//    }

    protected List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == ordinal) {
            return tabCompleteThis(sender, args);
        } else {
            return tabCompleteNext(sender, args);
        }
    }

    private List<String> tabCompleteThis(CommandSender sender, String[] args) {
        final List<String> out = new ArrayList<>();

        for (final ArgumentContent<T> content : contentSet) {
            if (!content.hasPermission(sender)) continue;
            final Set<String> contentArgs = content.getTapCompleteArgs(args[ordinal - 1]);
            if (contentArgs == null) {
                if (contentSet.size() == 1) return null;
                else out.addAll(Bukkit.getOnlinePlayers()
                        .stream().map(Player::getName).collect(Collectors.toSet()));
            } else {
                out.addAll(contentArgs);
            }
        }
        return out;
    }

    private List<String> tabCompleteNext(CommandSender sender, String[] args) {
        final List<String> out = new ArrayList<>();
        for (final AbstractArgument<P, T> argument : argumentSet) {
            if (!sender.hasPermission(permission)) continue;
            if (!argument.isTargetArgumentTabComplete(args[ordinal])) continue;
            final List<String> argumentArgs = argument.tabComplete(sender, args);
            if (argumentArgs == null) return null;
            out.addAll(argumentArgs);
        }
        return out;
    }

    private boolean isTargetArgumentTabComplete(String arg) {
        if (arg.isEmpty()) return true;
        for (final ArgumentContent<T> content : contentSet) {
            if (content.getArgs() == null) return true;
            if (!content.getTapCompleteArgs(arg).isEmpty()) return true;
        }
        return false;
    }

    private boolean isTargetArgument(String arg) {
        log("---------------------=isTargetArgument+Start=----------------------------");
        log("ARG: \"%s\"".formatted(arg));
        if (arg.isEmpty()) {
            log("arg is empty");
            log("---------------------=isTargetArgument+END=----------------------------");
            return true;
        }
        for (final ArgumentContent<T> content : contentSet) {
            if (content.getArgs() == null) {
                log("content.getArgs() == null");
                log("---------------------=isTargetArgument+END=----------------------------");
                return true;
            }
            if (!content.isTargetArg(arg)) {
                log("!content.isTargetArg(arg)");
                log("---------------------=isTargetArgument+END=----------------------------");
                return true;
            }
        }
        log("return false");
        log("---------------------=isTargetArgument+END=----------------------------");
        return false;
    }

//    private boolean isTargetArgument(String arg) {
//        if (arg.isEmpty()) return true;
//        for (final ArgumentContent<T> content : contentSet) {
//            if (content.getArgs() == null) return true;
//            if (!content.isTargetArg(arg)) return true;
//        }
//        return false;
//    }

    protected boolean senderNoPermission(CommandSender sender) {
        if (sender.hasPermission(permission)) return false;
        sender.sendMessage(noPermissionMessage);
        return true;
    }

    private void log(Object o) {
        Bukkit.getLogger().info("[SLib] " + o.toString());
    }
}
