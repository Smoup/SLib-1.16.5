package splug.slib.commands.content;

import org.bukkit.command.CommandSender;
import splug.slib.commands.data.CommandData;

import java.util.Set;

public interface ArgumentContent<T extends CommandData> {
    Set<String> getArgs();
    @Deprecated
    Set<String> getArgs(String s);
    Set<String> getTapCompleteArgs(String s);
    boolean isTargetArg(String s);
    boolean hasPermission(CommandSender sender);
    void handleArgumentData(ArgumentHandleData<T> handleData);
}
