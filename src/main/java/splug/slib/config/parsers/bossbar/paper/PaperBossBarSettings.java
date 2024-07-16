package splug.slib.config.parsers.bossbar.paper;

import net.kyori.adventure.bossbar.BossBar;

import java.util.Set;

public record PaperBossBarSettings(BossBar.Color color, BossBar.Overlay overlay, Set<BossBar.Flag> flags) {
}
