package splug.slib.commands.content;

import org.bukkit.command.CommandSender;
import splug.slib.commands.args.ArgumentData;

import java.util.Set;

public interface ArgumentContent<T extends ArgumentData> {
    Set<String> getArgs(String s);
    boolean hasPermission(CommandSender sender);
    void handleArgumentData(CommandSender sender, String[] args, T data, int ordinal);
}
