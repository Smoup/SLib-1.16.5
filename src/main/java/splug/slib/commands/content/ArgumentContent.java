package splug.slib.commands.content;

import org.bukkit.command.CommandSender;
import splug.slib.commands.CommandData;

import java.util.Set;

public interface ArgumentContent<T extends CommandData> {
    Set<String> getArgs();
    Set<String> getArgs(String s);
    boolean hasPermission(CommandSender sender);
    void handleArgumentData(ArgumentHandleData<T> handleData);
}
