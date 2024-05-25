package splug.slib.commands.samples.content.doubleNum;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.args.HandleArgumentDataException;
import splug.slib.commands.content.AbstractArgumentContent;
import splug.slib.commands.content.ArgData;

import java.util.Set;

@SuppressWarnings("unused")
@ToString @EqualsAndHashCode(callSuper = true)
public class DoubleContent<P extends JavaPlugin, T extends DoubleData>
        extends AbstractArgumentContent<P, T> {

    private static final Set<String> doubleNums =
            Set.of("1.0", "2.0", "3.0", "4.0", "5.0", "6.0", "7.0", "8.0", "9.0", "10.0");
    private String incorrectMSG = "§8[§6%plugin%§8] §b%s §cне число";

    public DoubleContent(P plugin, String permission) {
        super(plugin, permission, doubleNums);
        parseIncorrectMSG(plugin.getName());
    }

    public DoubleContent(P plugin, String permission, Set<String> args) {
        super(plugin, permission, args);
        parseIncorrectMSG(plugin.getName());
    }

    public DoubleContent(P plugin, String permission, String pluginName) {
        super(plugin, permission, doubleNums);
        parseIncorrectMSG(pluginName);
    }

    public DoubleContent(P plugin, String permission, String pluginName, Set<String> args) {
        super(plugin, permission, args);
        parseIncorrectMSG(pluginName);
    }

    private void parseIncorrectMSG(String pluginName) {
        incorrectMSG = incorrectMSG.replace("%plugin%", pluginName);
    }

    @Override
    public void handleArgumentData(ArgData<T> argData) {
        final String numString = argData.getTargetArg();
        final double number;
        try {
            number = Double.parseDouble(numString);
        } catch (NumberFormatException e) {
            argData.sender().sendMessage(incorrectMSG.formatted(numString));
            throw new HandleArgumentDataException();
        }
        argData.data().setDoubleNumber(number);
    }
}
