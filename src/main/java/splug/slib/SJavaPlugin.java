package splug.slib;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.CommandManager;

@Getter @ToString
public abstract class SJavaPlugin extends JavaPlugin {

    private final CommandManager commandManager = new CommandManager();
}
