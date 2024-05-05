package splug.slib.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

@Getter @Setter @ToString
@SuppressWarnings("unused")
public class CommandManager {

    private final HashSet<AbstractCommand<?>> commands = new HashSet<>();

    private String noPermissionMsg = "§8[§6%plugin-name%§8] §cYou don't have permissions";
    private String pluginNameText;

    public void setPluginName(JavaPlugin plugin) {
        setPluginNameText(plugin.getName());
    }

    public void setPluginName(String pluginName) {
        setPluginNameText(pluginName);
    }

    public void registerCommand(AbstractCommand<?> command) {
        commands.add(command);
    }

    public String getNoPermissionMsg() {
        return noPermissionMsg.replace("%plugin-name%", getPluginNameText());
    }
}
