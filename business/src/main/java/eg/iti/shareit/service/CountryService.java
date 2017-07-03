/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.service;

import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.model.dao.CountryDao;
import eg.iti.shareit.model.dto.CountryDto;
import eg.iti.shareit.model.entity.CountryEntity;
import eg.iti.shareit.model.util.MappingUtil;
import javax.ejb.EJB;

/**
 *
 * @author Dina Ashraf
 */
public class CountryService {
    @EJB
    private CountryDao countryDao;
    @EJB(beanName = "MappingUtil")
    private MappingUtil mappingUtil;
    
    public CountryDto getCountry(String country) throws DatabaseRollbackException {
        CountryEntity countryEntity = countryDao.getCountryByName(country);
        return mappingUtil.getDto(countryEntity, CountryDto.class);
    }
}
