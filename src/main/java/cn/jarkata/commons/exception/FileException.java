package cn.jarkata.commons.exception;

import cn.jarkata.commons.utils.StringUtils;

/**
 * 文件处理异常
 */
public class FileException extends RuntimeException {

    private String message;

    public FileException(Exception exception) {
        super(exception);
    }

    public FileException(Exception exception, String message) {
        super(message, exception);
        this.message = message;
    }

    @Override
    public String getMessage() {
        if (StringUtils.isNotBlank(message)) {
            return message;
        }
        return super.getMessage();
    }
}
