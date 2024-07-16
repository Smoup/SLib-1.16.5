package splug.slib.config.parsers.bossbar;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;

import java.util.Set;

public record BossBarSettings(BarColor barColor, BarStyle barStyle, Set<BarFlag> barFlags) {
}
