package splug.slib.commands.samples.content.integerNum;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.args.HandleArgumentDataException;
import splug.slib.commands.content.AbstractArgumentContent;

import java.util.Set;

@SuppressWarnings("unused")
@ToString @EqualsAndHashCode(callSuper = true)
public class IntegerContent<P extends JavaPlugin, T extends IntegerData>
        extends AbstractArgumentContent<P, T> {

    private static final Set<String> intNums = Set.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    private String incorrectMSG = "§8[§6%plugin%§8] §b%s §cне число";

    public IntegerContent(P plugin, String permission) {
        super(plugin, permission, intNums);
        parseIncorrectMSG(plugin.getName());
    }

    public IntegerContent(P plugin, String permission, String... args) {
        super(plugin, permission, args);
        parseIncorrectMSG(plugin.getName());
    }

    public IntegerContent(P plugin, String permission, Set<String> args) {
        super(plugin, permission, args);
        parseIncorrectMSG(plugin.getName());
    }

    public IntegerContent(P plugin, String permission, String pluginName) {
        super(plugin, permission, intNums);
        parseIncorrectMSG(pluginName);
    }

    public IntegerContent(P plugin, String permission, String pluginName, Set<String> args) {
        super(plugin, permission, args);
        parseIncorrectMSG(pluginName);
    }

    private void parseIncorrectMSG(String pluginName) {
        incorrectMSG = incorrectMSG.replace("%plugin%", pluginName);
    }

    @Override
    public void handleArgumentData(CommandSender sender, String[] args, T data, int ordinal) {
        final int number;
        try {
            number = Integer.parseInt(args[ordinal - 1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(incorrectMSG.formatted(args[ordinal - 1]));
            throw new HandleArgumentDataException();
        }
        data.setIntegerNumber(number);
    }
}
