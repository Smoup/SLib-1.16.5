package splug.slib.commands.data;

import lombok.Data;
import splug.slib.commands.CommandData;

@Data @SuppressWarnings("unused")
public class EmptyCommandData implements CommandData {
    @Override
    public boolean isValid(String[] args) {
        return true;
    }
}
