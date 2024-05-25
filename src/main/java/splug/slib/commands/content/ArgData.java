package splug.slib.commands.content;

import org.bukkit.command.CommandSender;
import splug.slib.commands.args.ArgumentData;

public record ArgData<T extends ArgumentData>(CommandSender sender, String[] args, T data, int ordinal, boolean itLast) {
    public String getTargetArg() {
        return args[ordinal - 1];
    }
}
