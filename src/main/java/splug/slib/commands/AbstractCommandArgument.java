package splug.slib.commands;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.command.CommandSender;

import java.util.*;

@Getter @ToString @SuppressWarnings("unused")
public abstract class AbstractCommandArgument {

    private final String name;
    private final String permission;
    private final HashMap<Integer, List<Set<String>>> parameters = new HashMap<>();


    public AbstractCommandArgument(String name, String parentPerm) {
        this.name = name;
        this.permission = "%s.%s".formatted(parentPerm, name);
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

    public abstract boolean execute(CommandSender sender, String label, String[] args);
}
