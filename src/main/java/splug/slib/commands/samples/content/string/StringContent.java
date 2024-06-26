package splug.slib.commands.samples.content.string;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.content.AbstractArgumentContent;
import splug.slib.commands.content.ArgumentHandleData;
import splug.slib.commands.data.CommandData;
import splug.slib.commands.exception.HandleArgumentDataException;

import java.util.Set;

@ToString @EqualsAndHashCode(callSuper = true) @SuppressWarnings("unused")
public class StringContent<P extends JavaPlugin, T extends CommandData>
        extends AbstractArgumentContent<P, T> {

    public StringContent(P plugin, String permission, String... args) {
        super(plugin, permission, args);
    }

    public StringContent(P plugin, String permission, Set<String> args) {
        super(plugin, permission, args);
    }

    @Override
    public void handleArgumentData(ArgumentHandleData<T> handleData) {
        if (!getArgs().contains(handleData.getTargetArg())) {
            throw new HandleArgumentDataException(handleData.isLast());
        }
    }
}
