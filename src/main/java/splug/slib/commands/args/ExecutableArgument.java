package splug.slib.commands.args;

import org.bukkit.command.CommandSender;
import splug.slib.commands.CommandData;

public interface ExecutableArgument<T extends CommandData> {
    void execute(CommandSender sender, String[] args, T data);
}
