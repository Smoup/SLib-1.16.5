package splug.slib.commands.args;

import lombok.ToString;

import java.util.Set;

@ToString @SuppressWarnings("unused")
public class ArgumentPlayerList extends Argument {
    public ArgumentPlayerList(String permission) {
        super(permission + "player-list", (Set<String>) null);
    }
}
