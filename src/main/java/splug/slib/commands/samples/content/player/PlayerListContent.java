package splug.slib.commands.samples.content.player;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.args.HandleArgumentDataException;
import splug.slib.commands.content.AbstractArgumentContent;

import java.util.Set;

@SuppressWarnings("unused")
@ToString @EqualsAndHashCode(callSuper = true)
public class PlayerListContent<P extends JavaPlugin, T extends PlayerData>
        extends AbstractArgumentContent<P, T> {
    private String incorrectMSG = "§8[§6%plugin%§8] §cИгрок §6%s §cне найден";

    public PlayerListContent(P plugin, String permission) {
        super(plugin, permission, (Set<String>) null);
        parseIncorrectMSG(plugin.getName());
    }

    public PlayerListContent(P plugin, String permission, String pluginName) {
        super(plugin, permission, (Set<String>) null);
        parseIncorrectMSG(pluginName);
    }

    private void parseIncorrectMSG(String pluginName) {
        incorrectMSG = incorrectMSG.replace("%plugin%", pluginName);
    }

    @Override
    public void handleArgumentData(CommandSender sender, String[] args, T data, int ordinal) {
        final Player player = Bukkit.getPlayerExact(args[ordinal - 1]);
        if (player == null) {
            sender.sendMessage(incorrectMSG.formatted(args[ordinal - 1]));
            throw new HandleArgumentDataException();
        }
        data.setPlayer(player);
    }
}
