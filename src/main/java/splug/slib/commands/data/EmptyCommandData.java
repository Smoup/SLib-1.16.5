package splug.slib.commands.data;

import lombok.Data;

@Data @SuppressWarnings("unused")
public final class EmptyCommandData implements CommandData {
    @Override
    public boolean isValid(String[] args) {
        return true;
    }
}
