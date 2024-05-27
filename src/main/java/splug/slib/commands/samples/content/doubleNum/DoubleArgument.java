package splug.slib.commands.samples.content.doubleNum;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.AbstractArgument;

@ToString
@EqualsAndHashCode(callSuper = true) @SuppressWarnings("unused")
public class DoubleArgument <P extends JavaPlugin, T extends DoubleCommandData> extends AbstractArgument<P, T> {

    public DoubleArgument(P plugin, int ordinal, String permission) {
        super(plugin, ordinal, permission + ".double-number-list");

        addContent(new DoubleArgumentContent<>(plugin, getPermission()));
    }

    public DoubleArgument(P plugin, int ordinal, String permission, String pluginName) {
        super(plugin, ordinal, permission + ".double-number-list");

        addContent(new DoubleArgumentContent<>(plugin, getPermission(), pluginName));
    }
}
