package splug.slib.commands.samples.content.doubleNum;

import splug.slib.commands.CommandData;

@SuppressWarnings("unused")
public interface DoubleCommandData extends CommandData {
    void setDoubleNumber(Double number);
    Double getDoubleNumber();
}
