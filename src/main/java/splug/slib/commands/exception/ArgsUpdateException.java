package splug.slib.commands.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import splug.slib.commands.content.ArgumentHandleData;
import splug.slib.commands.data.CommandData;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArgsUpdateException extends RuntimeException {

    private final ArgumentHandleData<?> handleData;

    public <T extends CommandData> ArgsUpdateException(ArgumentHandleData<T> handleData) {
        this.handleData = handleData;
    }
}
