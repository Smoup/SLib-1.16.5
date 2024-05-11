package splug.slib.commands.args;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Getter @RequiredArgsConstructor
@EqualsAndHashCode @ToString @SuppressWarnings("unused")
public class Argument {
    private final String permission;
    private final Set<String> params;

    public Argument(String permission, String... params) {
        this.permission = permission;
        this.params = Set.of(params);
    }
}
