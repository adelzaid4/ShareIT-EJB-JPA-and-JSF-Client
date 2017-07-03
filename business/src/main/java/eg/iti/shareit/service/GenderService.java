/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.service;

import eg.iti.shareit.model.dao.GenderDao;
import eg.iti.shareit.model.dto.GenderDto;
import eg.iti.shareit.model.entity.GenderEntity;
import eg.iti.shareit.model.util.MappingUtil;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author Dina Ashraf
 */
@Stateless
public class GenderService {
    @EJB
    private GenderDao genderDao;
    @EJB(beanName = "MappingUtil")
    private MappingUtil mappingUtil;
    
    public GenderDto getGender(String gender) {
        GenderEntity genderEntity = genderDao.getGenderByName(gender);
        return mappingUtil.getDto(genderEntity, GenderDto.class);
    }
}
