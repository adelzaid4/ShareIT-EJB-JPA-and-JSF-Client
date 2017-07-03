package eg.iti.shareit.common.Exception;

import javax.ejb.ApplicationException;

/**
 * Created by eltntawy on 29/09/15.
 */
@ApplicationException(rollback = true)
public class ServiceException extends AppException {

    public ServiceException() {
        super("Unknown Service error has bean occurred");

    }

    public ServiceException(String msg) {
        super(msg);
    }
}
