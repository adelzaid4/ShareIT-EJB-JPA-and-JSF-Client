/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.dao;

import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.common.dao.GenericDao;
import eg.iti.shareit.model.entity.CategoryEntity;
import java.util.List;

/**
 *
 * @author Yousef
 */
public interface CategoryDao extends GenericDao<CategoryEntity>{
    public CategoryEntity getCategoryByName(String name)throws DatabaseRollbackException;  
    public List<CategoryEntity> getAllCategories()throws DatabaseRollbackException;
    //    public List<CategoryEntity> AllCategoriesName() throws DatabaseRollbackException;
}
