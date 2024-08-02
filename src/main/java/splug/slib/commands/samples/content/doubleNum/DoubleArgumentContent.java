package splug.slib.commands.samples.content.doubleNum;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.content.AbstractArgumentContent;
import splug.slib.commands.content.ArgumentHandleData;
import splug.slib.commands.exception.HandleArgumentDataException;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@ToString @EqualsAndHashCode(callSuper = true)
public class DoubleArgumentContent<P extends JavaPlugin, T extends DoubleCommandData>
        extends AbstractArgumentContent<P, T> {

    private static final Set<String> doubleNums =
            Set.of("1.0", "2.0", "3.0", "4.0", "5.0", "6.0", "7.0", "8.0", "9.0", "10.0");
    private String incorrectMSG = "§8[§6%plugin%§8] §b%s §cне число";

    public DoubleArgumentContent(P plugin, String permission) {
        super(plugin, permission, doubleNums);
        parseIncorrectMSG(plugin.getName());
    }

    public DoubleArgumentContent(P plugin, String permission, Set<String> args) {
        super(plugin, permission, args);
        parseIncorrectMSG(plugin.getName());
    }

    public DoubleArgumentContent(P plugin, String permission, String pluginName) {
        super(plugin, permission, doubleNums);
        parseIncorrectMSG(pluginName);
    }

    public DoubleArgumentContent(P plugin, String permission, String pluginName, Set<String> args) {
        super(plugin, permission, args);
        parseIncorrectMSG(pluginName);
    }

    private void parseIncorrectMSG(String pluginName) {
        incorrectMSG = incorrectMSG.replace("%plugin%", pluginName);
    }

    @Override
    public void handleArgumentData(ArgumentHandleData<T> handleData) {
        final String numString = handleData.getTargetArg();
        final double number;
        try {
            number = Double.parseDouble(numString);
        } catch (NumberFormatException e) {
            handleData.getSender().sendMessage(incorrectMSG.formatted(numString));
            throw new HandleArgumentDataException(handleData.isLast());
        }

        handleData.getData().setDoubleNumber(number);
    }

    @Override
    public Set<String> getArgs(String prefix) {
        if (getArgs() == null) return null;
        final Set<String> out = getArgs().stream()
                .filter(s -> s.startsWith(prefix))
                .collect(Collectors.toSet());

        if (!out.isEmpty()) return out;
        out.add(prefix);
        return out;
    }


}
