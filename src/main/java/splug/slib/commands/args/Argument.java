package splug.slib.commands.args;

import lombok.Data;

import java.util.Set;

@Data @SuppressWarnings("unused")
public class Argument {
    private final String permission;
    private final Set<String> params;

    public Argument(String permission, String... params) {
        this.permission = permission + ".tab-complete";
        this.params = Set.of(params);
    }

    public Argument(String permission, Set<String> params) {
        this.permission = permission + ".tab-complete";
        this.params = params;
    }
}
