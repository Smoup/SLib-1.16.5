package splug.slib.commands.content;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.data.CommandData;

import java.util.Set;
import java.util.stream.Collectors;

@Data @ToString @EqualsAndHashCode
public abstract class AbstractArgumentContent<P extends JavaPlugin, T extends CommandData> implements ArgumentContent<T> {

    private final P plugin;

    private String permission;
    private Set<String> args;

    public AbstractArgumentContent(P plugin, String permission, String... args) {
        this(plugin, permission, Set.of(args));
    }

    public AbstractArgumentContent(P plugin, String permission, Set<String> args) {
        this.plugin = plugin;
        this.permission = permission;
        this.args = args;
    }

    @Override
    public Set<String> getArgs(String prefix) {
        final String lowerCasePrefix = prefix.toLowerCase();
        if (args == null) return null;
        return args.stream()
                .filter(s -> s.toLowerCase().startsWith(lowerCasePrefix))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getTapCompleteArgs(String prefix) {
        final String lowerCasePrefix = prefix.toLowerCase();
        if (args == null) return null;
        return args.stream()
                .filter(s -> s.toLowerCase().startsWith(lowerCasePrefix))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isTargetArg(String s) {
        for (final String arg : getArgs()) {
            if (arg.equalsIgnoreCase(s)) return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(permission);
    }
}
