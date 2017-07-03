/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.service;

import eg.iti.shareit.model.dao.CityDao;
import eg.iti.shareit.model.dto.CityDto;
import eg.iti.shareit.model.entity.CityEntity;
import eg.iti.shareit.model.util.MappingUtil;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Dina Ashraf
 */
@Stateless
public class CityService {
    @EJB
    private CityDao cityDao;
    @EJB(beanName = "MappingUtil")
    private MappingUtil mappingUtil;
    
    public CityDto getCity(String city) {
        CityEntity cityEntity = cityDao.getCityByName(city);
        return mappingUtil.getDto(cityEntity, CityDto.class);
    }
    
}
