package common.exceptions;

/**
 * Created by Mordr on 23.02.2017.
 * Исключение возникающие на уровне DAO
 */
public class DAOException extends Exception {
    public DAOException(Throwable cause) {
        super(cause);
    }
}
