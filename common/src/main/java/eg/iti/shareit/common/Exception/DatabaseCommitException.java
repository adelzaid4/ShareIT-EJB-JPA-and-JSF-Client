package eg.iti.shareit.common.Exception;

import javax.ejb.ApplicationException;

/**
 * Created by Mohamed on 2015/09/24.
 *
 * WPMSApplicationCommitException used to commit transaction first and then throw the exception
 */
@ApplicationException(rollback = false)
public class DatabaseCommitException extends DatabaseException {



    public DatabaseCommitException(String message) {
        super(message);
    }

}
