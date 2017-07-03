/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.dao;

import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.model.entity.CategoryEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Yousef
 */
@Stateless(mappedName = "CategoryDaoImpl")
public class CategoryDaoImpl extends GenericDaoImpl<CategoryEntity> implements CategoryDao {

    public CategoryDaoImpl() {
        super(CategoryEntity.class);
    }

    @Override
    public CategoryEntity getCategoryByName(String name) throws DatabaseRollbackException {

        Query query = getEntityManager().createQuery("Select c From CategoryEntity c where c.name = :name");
        query.setParameter("name", name);

        try {
            List<CategoryEntity> categoryList = query.getResultList();

            if (categoryList != null && categoryList.size() == 1) {
                return categoryList.get(0);
            } else {
                throw new DatabaseRollbackException("category  Not Found");
            }
        } catch (Exception ex) {
            throw new DatabaseRollbackException(ex.getMessage());
        }
    }

    @Override
    public List<CategoryEntity> getAllCategories() throws DatabaseRollbackException {
        Query query = getEntityManager().createQuery("Select c From CategoryEntity c ", CategoryEntity.class);

        try {
            List<CategoryEntity> categoryList = query.getResultList();
            if (categoryList != null) {
                return categoryList;
            } else {
                throw new DatabaseRollbackException(" No Categories Found");
            }
        } catch (PersistenceException ex) {
            throw new DatabaseRollbackException(ex.getMessage());
        }
    }
}
