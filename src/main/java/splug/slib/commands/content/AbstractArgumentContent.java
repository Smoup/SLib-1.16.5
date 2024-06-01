package splug.slib.commands.content;

import lombok.Data;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.data.CommandData;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public abstract class AbstractArgumentContent<P extends JavaPlugin, T extends CommandData> implements ArgumentContent<T> {

    private final P plugin;

    private String permission;
    private Set<String> args;

    public AbstractArgumentContent(P plugin, String permission, String... args) {
        this.plugin = plugin;
        this.permission = permission;
        this.args = Set.of(args);
    }

    public AbstractArgumentContent(P plugin, String permission, Set<String> args) {
        this.plugin = plugin;
        this.permission = permission;
        this.args = args;
    }

    @Override
    public Set<String> getArgs(String prefix) {
        if (args == null) return null;
        return args.stream()
                .filter(s -> s.toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(permission);
    }
}
