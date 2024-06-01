package splug.slib.commands.content;

import lombok.Data;
import org.bukkit.command.CommandSender;
import splug.slib.commands.data.CommandData;

@Data
public final class ArgumentHandleData<T extends CommandData> {
    private final CommandSender sender;
    private final String[] args;
    private final T data;
    private final int ordinal;
    private final boolean last;

    public ArgumentHandleData(CommandSender sender, String[] args, T data, int ordinal, boolean isLast) {
        this.sender = sender;
        this.args = args;
        this.data = data;
        this.ordinal = ordinal;
        this.last = isLast;
    }

    public String getTargetArg() {
        return args[ordinal - 1];
    }
}
