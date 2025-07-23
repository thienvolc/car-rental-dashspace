package dashspace.fun.car_rental_server.exception;

import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Object[] args;

    public BusinessException(ErrorCode errorCode, @Nullable Object... args) {
        super(getFormattedMessage(errorCode, args));
        this.errorCode = errorCode;
        this.args = args;
    }

    private static String getFormattedMessage(ErrorCode errorCode,
                                              @Nullable Object... args) {

        if (hasArguments(args)) {
            return String.format(errorCode.getDefaultMessage(), args);
        }
        return errorCode.getDefaultMessage();
    }

    private static boolean hasArguments(Object[] args) {
        return args != null && args.length > 0;
    }
}
