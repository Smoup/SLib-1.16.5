package splug.slib.commands.samples.content.integerNum;

import splug.slib.commands.args.ArgumentData;

public interface IntegerData extends ArgumentData {
    void setIntegerNumber(int number);
    int getIntegerNumber();
}
