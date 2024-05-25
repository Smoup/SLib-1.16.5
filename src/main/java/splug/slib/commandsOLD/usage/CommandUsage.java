package splug.slib.commandsOLD.usage;

import com.google.common.collect.Lists;

import java.util.List;

public class CommandUsage {
    private final String command;
    private final String description;
    private final List<String> parameters;

    public CommandUsage(String command, String description, String... parameters) {
        this.command = command;
        this.description = description;
        this.parameters = Lists.newArrayList(parameters);
    }

    public String getResult() {
        final String commandFormat = "    §6/%s ";
        final String parameterFormat = "§7<§6%s§7> ";
        final String descriptionFormat = "§7- %s\n";

        final StringBuilder builder = new StringBuilder(commandFormat.formatted(command));

        for (final String parameter : parameters) {
            builder.append(parameterFormat.formatted(parameter));
        }

        builder.append(descriptionFormat.formatted(description));

        return builder.toString();
    }
}
