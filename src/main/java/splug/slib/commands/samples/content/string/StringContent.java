package splug.slib.commands.samples.content.string;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.args.ArgumentData;
import splug.slib.commands.content.AbstractArgumentContent;

import java.util.Set;

@ToString @EqualsAndHashCode(callSuper = true) @SuppressWarnings("unused")
public class StringContent<P extends JavaPlugin, T extends ArgumentData>
        extends AbstractArgumentContent<P, T> {
    private String incorrectMSG = "§8[§6%plugin%§8] §cНеизвестный аргумент команды §6%s";

    public StringContent(P plugin, String permission, String... args) {
        super(plugin, permission, args);
        parseIncorrectMSG(plugin.getName());
    }

    public StringContent(P plugin, String permission, Set<String> args) {
        super(plugin, permission, args);
        parseIncorrectMSG(plugin.getName());
    }

    public StringContent(P plugin, String permission, String pluginName, Set<String> args) {
        super(plugin, permission, args);
        parseIncorrectMSG(pluginName);
    }

    private void parseIncorrectMSG(String pluginName) {
        incorrectMSG = incorrectMSG.replace("%plugin%", pluginName);
    }

    @Override
    public void handleArgumentData(CommandSender sender, String[] args, T data, int ordinal) {
        if (getArgs().contains(args[ordinal - 1])) return;

        sender.sendMessage(incorrectMSG.formatted(args[ordinal - 1]));
    }
}
