package splug.slib.commands.samples.content.doubleNum;

import splug.slib.commands.args.ArgumentData;

@SuppressWarnings("unused")
public interface DoubleData extends ArgumentData {
    void setDoubleNumber(double number);
    double getDoubleNumber();
}
