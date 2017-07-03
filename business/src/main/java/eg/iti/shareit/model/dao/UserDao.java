package eg.iti.shareit.model.dao;

import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.common.dao.GenericDao;
import eg.iti.shareit.model.dto.UserDto;
import eg.iti.shareit.model.entity.UserEntity;
import java.math.BigDecimal;


/**
 * Created by Mohamed on 2015/07/04.
 */
public interface UserDao extends GenericDao<UserEntity> {

    public UserEntity getUserByEmail(String email) throws DatabaseRollbackException;
    public UserEntity findUser(String email, String password) throws DatabaseRollbackException;
    public boolean saveUser(UserEntity user)throws DatabaseRollbackException;
    public boolean updateUser(UserEntity user)throws DatabaseRollbackException;
    public UserEntity getUserById(BigDecimal id)throws DatabaseRollbackException;
            
}
