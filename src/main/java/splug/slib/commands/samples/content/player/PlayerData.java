package splug.slib.commands.samples.content.player;

import org.bukkit.entity.Player;
import splug.slib.commands.args.ArgumentData;

public interface PlayerData extends ArgumentData {
    void setPlayer(Player player);
    Player getPlayer();
}
