package splug.slib.commands.samples.content.integerNum;

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
public class IntegerArgumentContent<P extends JavaPlugin, T extends IntegerCommandData>
        extends AbstractArgumentContent<P, T> {

    private static final Set<String> intNums = Set.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    private String incorrectMSG = "§8[§6%plugin%§8] §b%s §cне число";

    public IntegerArgumentContent(P plugin, String permission) {
        super(plugin, permission, intNums);
        parseIncorrectMSG(plugin.getName());
    }

    public IntegerArgumentContent(P plugin, String permission,  Set<String> args) {
        super(plugin, permission, args);
        parseIncorrectMSG(plugin.getName());
    }

    public IntegerArgumentContent(P plugin, String permission, String pluginName) {
        super(plugin, permission, intNums);
        parseIncorrectMSG(pluginName);
    }

    public IntegerArgumentContent(P plugin, String permission, String pluginName, Set<String> args) {
        super(plugin, permission, args);
        parseIncorrectMSG(pluginName);
    }

    private void parseIncorrectMSG(String pluginName) {
        incorrectMSG = incorrectMSG.replace("%plugin%", pluginName);
    }

    @Override
    public void handleArgumentData(ArgumentHandleData<T> handleData) {
        final String numString = handleData.getTargetArg();
        final int number;
        try {
            number = Integer.parseInt(numString);
        } catch (NumberFormatException e) {
            handleData.getSender().sendMessage(incorrectMSG.formatted(numString));
            throw new HandleArgumentDataException(handleData.isLast());
        }
        handleData.getData().setIntegerNumber(number);
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
