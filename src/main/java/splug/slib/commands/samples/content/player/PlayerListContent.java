package splug.slib.commands.samples.content.player;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.args.HandleArgumentDataException;
import splug.slib.commands.content.AbstractArgumentContent;
import splug.slib.commands.content.ArgData;

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
    public void handleArgumentData(ArgData<T> argData) {
        final String playerName = argData.getTargetArg();
        final Player player = Bukkit.getPlayerExact(playerName);
        if (player == null) {
            argData.sender().sendMessage(incorrectMSG.formatted(playerName));
            throw new HandleArgumentDataException();
        }
        argData.data().setPlayer(player);
    }
}
