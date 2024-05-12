package splug.slib.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor @Getter
@EqualsAndHashCode @ToString @SuppressWarnings("unused")
public class CommandManager {
    private final Set<AbstractCommand> commands = new HashSet<>();

    public void register(AbstractCommand command) {
        commands.add(command);
    }
}
