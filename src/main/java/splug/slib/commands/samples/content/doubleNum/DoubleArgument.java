package splug.slib.commands.samples.content.doubleNum;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.AbstractArgument;
import splug.slib.commands.usage.CommandUsageExecutor;

@ToString
@EqualsAndHashCode(callSuper = true) @SuppressWarnings("unused")
public class DoubleArgument <P extends JavaPlugin, T extends DoubleData> extends AbstractArgument<P, T> {

    public DoubleArgument(P plugin, int ordinal, String permission, CommandUsageExecutor cmdUsage) {
        super(plugin, ordinal, permission + ".double-number-list", cmdUsage);

        addContent(new DoubleContent<>(plugin, getPermission()));
    }

    public DoubleArgument(P plugin, int ordinal, String permission, CommandUsageExecutor cmdUsage, String pluginName) {
        super(plugin, ordinal, permission + ".double-number-list", cmdUsage);

        addContent(new DoubleContent<>(plugin, getPermission(), pluginName));
    }
}
