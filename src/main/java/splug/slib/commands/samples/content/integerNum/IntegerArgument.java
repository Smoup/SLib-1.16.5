package splug.slib.commands.samples.content.integerNum;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.AbstractArgument;
import splug.slib.commands.usage.CommandUsageExecutor;

@ToString
@EqualsAndHashCode(callSuper = true) @SuppressWarnings("unused")
public class IntegerArgument <P extends JavaPlugin, T extends IntegerData> extends AbstractArgument<P, T> {

    public IntegerArgument(P plugin, int ordinal, String permission, CommandUsageExecutor cmdUsage) {
        super(plugin, ordinal, permission + ".integer-number-list", cmdUsage);

        addContent(new IntegerContent<>(plugin, getPermission()));
    }

    public IntegerArgument(P plugin, int ordinal, String permission, CommandUsageExecutor cmdUsage, String pluginName) {
        super(plugin, ordinal, permission + ".integer-number-list", cmdUsage);

        addContent(new IntegerContent<>(plugin, getPermission(), pluginName));
    }
}
