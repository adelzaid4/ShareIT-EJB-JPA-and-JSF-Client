package eg.iti.shareit.model.dao;

import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.common.dao.GenericDao;
import eg.iti.shareit.common.entity.GenericEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 * Created by Mohamed on 2015/07/04.
 */
public abstract class GenericDaoImpl<T extends GenericEntity> implements GenericDao<T> {

    private Class<T> type;

    @PersistenceContext(unitName = "shareitPersistenceUnit")
    protected EntityManager em;

    public GenericDaoImpl(Class<T> type) {
        this.type = type;
    }

    public T get(BigDecimal id) throws DatabaseRollbackException {
        try {
            if (id == null) {
                return null;
            } else {
                
                return em.find(type, id);
            }
        } catch (PersistenceException ex) {
            throw new DatabaseRollbackException(ex.getMessage());
        }
    }

    public List<T> getAll() throws DatabaseRollbackException {
        try {
            return em.createQuery("From " + type.getSimpleName() + "").getResultList();
        } catch (PersistenceException ex) {
            throw new DatabaseRollbackException(ex.getMessage());
        }
    }

    public List<T> getAll(BigDecimal id) throws DatabaseRollbackException {
        try {
            if (id == null) {
                return null;
            } else {
                return em.createQuery("from " + type.getSimpleName() + " where id=" + id + "").getResultList();
            }
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    public void save(T object) throws DatabaseRollbackException {
        try {
            em.persist(object);
        } catch (PersistenceException ex) {
            throw new DatabaseRollbackException(ex.getMessage());
        }
    }

    public void delete(T object) throws DatabaseRollbackException {
        try {
            if (!em.contains(object)) {
                object = em.merge(object);
            }
            em.remove(object);
        } catch (PersistenceException ex) {
            throw new DatabaseRollbackException(ex.getMessage());
        }
    }

    public void delete(BigDecimal id) throws DatabaseRollbackException {
        try {
            T object = get(id);
            delete(object);
        } catch (PersistenceException ex) {
            throw new DatabaseRollbackException(ex.getMessage());
        }
    }

    public void update(T object) throws DatabaseRollbackException {
        try {
            em.merge(object);
            em.flush();
        } catch (PersistenceException ex) {
            throw new DatabaseRollbackException(ex.getMessage());
        }
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
