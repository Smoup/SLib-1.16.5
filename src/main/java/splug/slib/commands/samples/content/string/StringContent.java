package splug.slib.commands.samples.content.string;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.args.ArgumentData;
import splug.slib.commands.args.HandleArgumentDataException;
import splug.slib.commands.content.AbstractArgumentContent;
import splug.slib.commands.content.ArgData;

import java.util.Set;

@ToString @EqualsAndHashCode(callSuper = true) @SuppressWarnings("unused")
public class StringContent<P extends JavaPlugin, T extends ArgumentData>
        extends AbstractArgumentContent<P, T> {

    public StringContent(P plugin, String permission, String... args) {
        super(plugin, permission, args);
    }

    public StringContent(P plugin, String permission, Set<String> args) {
        super(plugin, permission, args);
    }

    @Override
    public void handleArgumentData(ArgData<T> argData) {
        if (!getArgs().contains(argData.getTargetArg())) {
            throw new HandleArgumentDataException(argData.itLast());
        }
    }
}
