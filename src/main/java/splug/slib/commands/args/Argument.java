package splug.slib.commands.args;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data @RequiredArgsConstructor @SuppressWarnings("unused")
public class Argument {
    private final String permission;
    private final Set<String> params;

    public Argument(String permission, String... params) {
        this.permission = permission + ".tab-complete";
        this.params = Set.of(params);
    }
}
