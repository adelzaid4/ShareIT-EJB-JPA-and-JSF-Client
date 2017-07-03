package eg.iti.shareit.common.dao;

import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.common.entity.GenericEntity;
import java.math.BigDecimal;

import java.util.List;

public interface GenericDao<T extends GenericEntity> {

    T get(BigDecimal id) throws DatabaseRollbackException;

    List<T> getAll() throws DatabaseRollbackException;

    List<T> getAll(BigDecimal id) throws DatabaseRollbackException;

    void save(T object) throws DatabaseRollbackException;

    void update(T object) throws DatabaseRollbackException;

    void delete(T object) throws DatabaseRollbackException;

    void delete(BigDecimal id) throws DatabaseRollbackException;

}
