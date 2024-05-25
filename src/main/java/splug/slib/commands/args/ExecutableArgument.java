package splug.slib.commands.args;

import org.bukkit.command.CommandSender;

public interface ExecutableArgument<T extends ArgumentData> {
    void execute(CommandSender sender, T data);
}
