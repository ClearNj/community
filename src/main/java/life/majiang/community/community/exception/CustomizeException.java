package life.majiang.community.community.exception;

/**
 * Created by codedrinker on 2019/5/28.
 */
public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.
                getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

}
