package splug.slib.commands.samples.content.integerNum;

import splug.slib.commands.data.CommandData;

@SuppressWarnings("unused")
public interface IntegerCommandData extends CommandData {
    void setIntegerNumber(Integer number);
    Integer getIntegerNumber();
}
