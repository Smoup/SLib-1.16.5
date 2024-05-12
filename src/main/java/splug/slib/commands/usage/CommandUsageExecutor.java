package splug.slib.commands.usage;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@SuppressWarnings("unused")
public class CommandUsageExecutor {

    private String usageMessage = """ 
        \n§7§m%defises%§r§7> §b%plugin-name% By §x§D§B§A§9§0§0_§x§E§2§9§8§0§0S§x§E§9§8§6§0§0m§x§E§F§7§5§0§0o§x§F§6§6§3§0§0u§x§F§D§5§2§0§0p §7<§m%defises%§r
        \n
        §7Версия: §6%version%
        \n
        §7Список команд:
        \n
    %commands%
        \n
    §7§m%defises%---------------%defises%
    """;

    private final List<CommandUsage> commands = Lists.newArrayList();

    public CommandUsageExecutor(String pluginName) {
        handleMessage(pluginName);
    }

    private void handleMessage(String pluginName) {
        final StringBuilder sBuilder = new StringBuilder("--------------------");

        final int from = sBuilder.length() - ((int) Math.floor((double) pluginName.length() / 2));

        sBuilder.delete(from, sBuilder.length());

        usageMessage = usageMessage.replace("%plugin-name%", pluginName);
        usageMessage = usageMessage.replace("%defises%", sBuilder.toString());
    }

    public void version(String version) {
        usageMessage = usageMessage.replace("%version%", version);
    }

    public void command(String command, String description, String... parameters) {
        commands.add(new CommandUsage(command, description, parameters));
    }

    public void build() {
        final StringBuilder sBuilder = new StringBuilder();

        for (final CommandUsage usage : commands) {
            sBuilder.append(usage.getResult());
        }

        setUsageMessage(usageMessage.replace("%commands%", sBuilder.toString()));
    }
}
