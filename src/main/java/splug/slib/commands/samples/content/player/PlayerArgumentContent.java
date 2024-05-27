package splug.slib.commands.samples.content.player;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import splug.slib.commands.content.AbstractArgumentContent;
import splug.slib.commands.content.ArgumentHandleData;
import splug.slib.commands.exception.HandleArgumentDataException;

import java.util.Set;

@SuppressWarnings("unused")
@ToString @EqualsAndHashCode(callSuper = true)
public class PlayerArgumentContent<P extends JavaPlugin, T extends PlayerCommandData>
        extends AbstractArgumentContent<P, T> {
    private String incorrectMSG = "§8[§6%plugin%§8] §cИгрок §6%s §cне найден";

    public PlayerArgumentContent(P plugin, String permission) {
        super(plugin, permission, (Set<String>) null);
        parseIncorrectMSG(plugin.getName());
    }

    public PlayerArgumentContent(P plugin, String permission, String pluginName) {
        super(plugin, permission, (Set<String>) null);
        parseIncorrectMSG(pluginName);
    }

    private void parseIncorrectMSG(String pluginName) {
        incorrectMSG = incorrectMSG.replace("%plugin%", pluginName);
    }

    @Override
    public void handleArgumentData(ArgumentHandleData<T> handleData) {
        final String playerName = handleData.getTargetArg();
        final Player player = Bukkit.getPlayerExact(playerName);
        if (player == null) {
            handleData.getSender().sendMessage(incorrectMSG.formatted(playerName));
            throw new HandleArgumentDataException(handleData.isLast());
        }
        handleData.getData().setPlayer(player);
    }
}
