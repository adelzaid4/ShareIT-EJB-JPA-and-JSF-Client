/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.service;

import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.common.Exception.ServiceException;
import eg.iti.shareit.model.dao.AddressDao;
import eg.iti.shareit.model.dao.CountryDao;
import eg.iti.shareit.model.dto.AddressDto;
import eg.iti.shareit.model.dto.CityDto;
import eg.iti.shareit.model.dto.CountryDto;
import eg.iti.shareit.model.dto.StateDto;
import eg.iti.shareit.model.entity.AddressEntity;
import eg.iti.shareit.model.entity.CityEntity;
import eg.iti.shareit.model.entity.CountryEntity;
import eg.iti.shareit.model.entity.StateEntity;
import eg.iti.shareit.model.util.MappingUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

/**
 *
 * @author Dina Ashraf
 */
@Singleton
public class AddressService {

    @EJB(beanName = "AddressDaoImpl")
    private AddressDao addressDao;

    @EJB(beanName = "CountryDaoImpl")
    private CountryDao countryDao;
    @EJB(beanName = "MappingUtil")
    private MappingUtil mappingUtil;

    private static final Logger logger = Logger.getLogger(AddressService.class.getName());

    public AddressDto getAddress(AddressDto addressDto) throws ServiceException{
        try{
            AddressEntity addressEntity = addressDao.getAddress(mappingUtil.getEntity(addressDto, AddressEntity.class));
            return mappingUtil.getDto(addressEntity, AddressDto.class);
        }catch (DatabaseRollbackException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ServiceException(ex.getMessage());
        }
    }
    public List<AddressDto> getAllAddresses() throws ServiceException {
        try {
            List<AddressEntity> addresses = addressDao.getAll();
            return mappingUtil.getDtoList(addresses, AddressDto.class);
        } catch (DatabaseRollbackException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public List<StateDto> getStates(BigDecimal countryId) throws ServiceException {
        try {
            List<StateEntity> states = addressDao.getRelatedStates(countryId);
            return mappingUtil.getDtoList(states, StateDto.class);
        } catch (DatabaseRollbackException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public List<CityDto> getCities(BigDecimal countryId) throws ServiceException {
        try {
            List<CityEntity> cities = addressDao.getRelatedCities(countryId);
            return mappingUtil.getDtoList(cities, CityDto.class);
        } catch (DatabaseRollbackException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public List<CityDto> getCities(BigDecimal countryId, BigDecimal stateId) throws ServiceException {
        try {
            List<CityEntity> cities = addressDao.getRelatedCities(countryId, stateId);
            return mappingUtil.getDtoList(cities, CityDto.class);
        } catch (DatabaseRollbackException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public List<CountryDto> getCountries() throws ServiceException {
        try {
            List<CountryEntity> countries = countryDao.getAll();
            return mappingUtil.getDtoList(countries, CountryDto.class);
        } catch (DatabaseRollbackException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public CountryDto getCountry(BigDecimal id) throws ServiceException {
        try {
            CountryEntity country = countryDao.get(id);
            return mappingUtil.getDto(country, CountryDto.class);
        } catch (DatabaseRollbackException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
