package splug.slib.commands.samples.content.player;

import org.bukkit.entity.Player;
import splug.slib.commands.CommandData;

@SuppressWarnings("unused")
public interface PlayerCommandData extends CommandData {
    void setPlayer(Player player);
    Player getPlayer();
}
