package splug.slib.commands;

import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import splug.slib.SJavaPlugin;

import java.util.*;
import java.util.stream.IntStream;

@Getter @ToString @SuppressWarnings("unused")
@EqualsAndHashCode
public abstract class AbstractCommand<T extends SJavaPlugin> implements CommandExecutor, TabCompleter {

    private final String permission;
    private final HashMap<String, AbstractCommandArgument> arguments = new HashMap<>();
    private final HashMap<Integer, List<Set<String>>> parameters = new HashMap<>();
    private final T plugin;

    @Setter
    private String noPermissionsMSG;


    public AbstractCommand(String command, T plugin) {
        this.plugin = plugin;
        final PluginCommand pluginCMD = plugin.getCommand(command);

        if (pluginCMD != null) {
            permission = pluginCMD.getPermission();
            pluginCMD.setExecutor(this);
        } else {
            permission = null;
        }

        noPermissionsMSG = "§8[§6%s§8] §cYou don't have permissions".formatted(plugin.getName());
    }

    public abstract boolean execute(CommandSender sender, String label, String[] args);

    @Override
    public boolean onCommand(@NonNull CommandSender sender,
                             @NonNull Command command,
                             @NonNull String s,
                             @NonNull String[] args) {
        return execute(sender, s, args);
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender,
                                      @NonNull Command command,
                                      @NonNull String alias,
                                      @NonNull String[] args) {
        return filter(complete(sender, args), args);
    }

    private List<String> filter(List<String> list, String[] args) {
        if (list == null) return List.of();

        final String last = args[args.length -1];
        final List<String> result = new ArrayList<>();

        for (String arg : list) {
            if (arg.toLowerCase().startsWith(last.toLowerCase())) result.add(arg);
        }

        return result;
    }

    public boolean executeArguments(CommandSender sender, String label, String[] args) {
        if (args.length < 1) return false;
        final var targetArg = parseArgument(args);

        if (targetArg == null) return false;

        if (!sender.hasPermission(targetArg.getPermission())) {
            sender.sendMessage(noPermissionsMSG);
            return true;
        }

        return targetArg.execute(sender, label, args);
    }

    private AbstractCommandArgument parseArgument(String[] args) {
        for (final String arg : args) {
            boolean contains = true;

            for (List<Set<String>> value : parameters.values()) {
                for (Set<String> strings : value) {
                    contains = strings.contains(arg);
                }
            }

            if (!contains) {
                return arguments.get(arg);
            }
        }

        return null;
    }

    /**
     * %players-list% - список онлайн игроков
     * <p>
     * %nums% - числа 1..9
     */
    public void addParameters(int ordinal, String... strings) {
        final var paramsList = getParameters().computeIfAbsent(ordinal, k -> List.of(new HashSet<>()));

        paramsList.get(0).addAll(List.of(strings));
    }

    public void addParameters(int ordinal, Set<String> parameterSet) {
        final var paramsList = getParameters().computeIfAbsent(ordinal, k -> new ArrayList<>());

        Set<String> general;

        if (paramsList.isEmpty()) {
            general = new HashSet<>();
            paramsList.add(general);
        }

        paramsList.add(parameterSet);
    }


    private List<String> complete(CommandSender sender, String[] args) {
        if (args.length < 1) return List.of();

        final ArrayList<String> tabCompleteList = new ArrayList<>();

        arguments.forEach((argumentName, argumentValue) -> {
            if (!sender.hasPermission(argumentValue.getPermission())) return;
            if (argumentValue.getOrdinal() != args.length) return;

            tabCompleteList.add(argumentName);
        });

        if (parameters.containsKey(args.length)) {
            handleParameters(parameters.get(args.length), tabCompleteList);
        }

        return handleArgumetsParameters(sender, args, tabCompleteList);
    }

    private ArrayList<String> handleArgumetsParameters(CommandSender sender, String[] args, ArrayList<String> tabCompleteList) {
        final var targetArgument = arguments.get(args[0]);
        if (targetArgument == null) return tabCompleteList;

        if (!sender.hasPermission(targetArgument.getPermission())) return tabCompleteList;

        final List<Set<String>> parametersList = targetArgument.getParameters().get(args.length);

        if (parametersList == null) return tabCompleteList;

        handleParameters(parametersList, tabCompleteList);

        return tabCompleteList;
    }

    private void handleParameters(List<Set<String>> parametersList, ArrayList<String> tabCompleteList) {
        for (Set<String> parameterSet : parametersList) {
            tabCompleteList.addAll(parameterSet);
            if (tabCompleteList.remove("%players-list%")) {
                tabCompleteList.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());
            }

            if (tabCompleteList.remove("%nums%")) {
                final List<String> numbersList = IntStream.rangeClosed(1, 9).mapToObj(Integer::toString).toList();
                tabCompleteList.addAll(numbersList);
            }
        }
    }

    public void addCommandArgument(AbstractCommandArgument abstractCommandArgument) {
       arguments.put(abstractCommandArgument.getName(), abstractCommandArgument);
    }
}
