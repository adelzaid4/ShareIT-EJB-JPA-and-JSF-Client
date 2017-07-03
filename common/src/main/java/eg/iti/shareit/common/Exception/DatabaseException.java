package eg.iti.shareit.common.Exception;

import javax.ejb.ApplicationException;

/**
 * Created by eltntawy on 29/09/15.
 */
@ApplicationException(rollback = true)
public abstract class DatabaseException extends AppException {

    public DatabaseException() {
        super("Unknown Database error has bean occurred");
    }

    public DatabaseException(String msg) {
        super(msg);
    }
}
