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
import eg.iti.shareit.model.entity.StateEntity;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Yousef
 */
public interface AddressDao extends GenericDao<AddressEntity> {

    public List<StateEntity> getRelatedStates(BigDecimal countryId) throws DatabaseRollbackException;

    public List<CityEntity> getRelatedCities(BigDecimal countryId) throws DatabaseRollbackException;

    public List<CityEntity> getRelatedCities(BigDecimal countryId, BigDecimal stateId) throws DatabaseRollbackException;
    public AddressEntity getAddress(AddressEntity addressDto) throws DatabaseRollbackException;
}
