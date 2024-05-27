package splug.slib.commands.samples.content.integerNum;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.AbstractArgument;

@ToString
@EqualsAndHashCode(callSuper = true) @SuppressWarnings("unused")
public class IntegerArgument <P extends JavaPlugin, T extends IntegerCommandData> extends AbstractArgument<P, T> {

    public IntegerArgument(P plugin, int ordinal, String permission) {
        super(plugin, ordinal, permission + ".integer-number-list");

        addContent(new IntegerArgumentContent<>(plugin, getPermission()));
    }

    public IntegerArgument(P plugin, int ordinal, String permission, String pluginName) {
        super(plugin, ordinal, permission + ".integer-number-list");

        addContent(new IntegerArgumentContent<>(plugin, getPermission(), pluginName));
    }
}
