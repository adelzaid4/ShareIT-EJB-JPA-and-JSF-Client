package eg.iti.shareit.common.Exception;

import javax.ejb.ApplicationException;

/**
 * Created by Mohamed on 2015/09/24.
 *
 * WPMSApplicationRollbackException is used to rollback the transaction first then throw exception
 */
@ApplicationException(rollback = true)
public class DatabaseRollbackException extends DatabaseException {


    public DatabaseRollbackException(String message) {
        super(message);
    }

}
