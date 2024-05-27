package splug.slib.commands.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true) @Data
public class HandleArgumentDataException extends RuntimeException {
    final boolean sendUsage;

    public HandleArgumentDataException() {
        this.sendUsage = false;
    }

    public HandleArgumentDataException(boolean sendUsage) {
        this.sendUsage = sendUsage;
    }
}
