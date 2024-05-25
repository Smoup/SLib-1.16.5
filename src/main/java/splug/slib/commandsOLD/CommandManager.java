package splug.slib.commandsOLD;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor @Data @SuppressWarnings("unused")
public class CommandManager {
    private final Set<AbstractCommand<?>> commands = new HashSet<>();

    public void register(AbstractCommand<?> command) {
        commands.add(command);
    }
}
