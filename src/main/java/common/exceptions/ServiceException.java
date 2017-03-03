package common.exceptions;

/**
 * Created by Mordr on 01.03.2017.
 * Исключение возникающее на уровне сервиса
 */
public class ServiceException extends Exception {
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
