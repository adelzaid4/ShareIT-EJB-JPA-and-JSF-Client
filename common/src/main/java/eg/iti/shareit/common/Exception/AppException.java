package eg.iti.shareit.common.Exception;

import javax.ejb.ApplicationException;

/**
 * Created by Mohamed_2 on 11/14/2015.
 */
@ApplicationException(rollback = true)
public abstract class AppException extends Exception {
    public AppException () {
        super("Unknown error has bean occurred");
    }

    public AppException (String message) {
        super(message);
    }
}
