/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.dao;

import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.common.dao.GenericDao;
import eg.iti.shareit.model.dto.AddressDto;
import eg.iti.shareit.model.entity.AddressEntity;
import eg.iti.shareit.model.entity.CityEntity;
import eg.iti.shareit.model.entity.CountryEntity;
import eg.iti.shareit.model.entity.ItemEntity;
import eg.iti.shareit.model.entity.StateEntity;
import java.util.List;

/**
 *
 * @author Yousef
 */
public interface ItemDao extends GenericDao<ItemEntity> {

    public List<ItemEntity> searchItem(String name, int category) throws DatabaseRollbackException;

    public List<ItemEntity> searchItem(String name) throws DatabaseRollbackException;

    public List<ItemEntity> searchItem(int category) throws DatabaseRollbackException;

    public boolean isItemAvailable(int itemId) throws DatabaseRollbackException;

    public boolean isPendeingRequest(int itemId) throws DatabaseRollbackException;

    public int addItem(ItemEntity item) throws DatabaseRollbackException;

    public List<ItemEntity> searchItem(CountryEntity countryEntity, StateEntity stateEntity, CityEntity cityEntity) throws DatabaseRollbackException;

    public List<ItemEntity> getRelatedItems(ItemEntity myItem) throws DatabaseRollbackException;

    public void updateItem(ItemEntity updateItem) throws DatabaseRollbackException;

    public boolean deleteItem(ItemEntity item) throws DatabaseRollbackException;
}
