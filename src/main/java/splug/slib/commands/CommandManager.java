package splug.slib.commands;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor @Data @SuppressWarnings("unused")
public class CommandManager<P extends JavaPlugin> {
    private final Set<AbstractCommand<P, ?>> commands = new HashSet<>();

    public void register(AbstractCommand<P, ?> command) {
        commands.add(command);
    }
}
