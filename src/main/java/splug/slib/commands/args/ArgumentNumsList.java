package splug.slib.commands.args;

import lombok.ToString;

import java.util.Set;

@ToString @SuppressWarnings("unused")
public class ArgumentNumsList extends Argument {
    public ArgumentNumsList(String permission) {
        super(permission + "nums-list", Set.of("1", "2", "3", "4", "5", "6", "7", "8", "9"));
    }
}
