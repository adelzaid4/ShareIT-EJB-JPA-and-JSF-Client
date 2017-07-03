/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.service;

import eg.iti.shareit.model.dao.StateDao;
import eg.iti.shareit.model.dto.StateDto;
import eg.iti.shareit.model.entity.StateEntity;
import eg.iti.shareit.model.util.MappingUtil;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Dina Ashraf
 */
@Stateless
public class StateService {

    @EJB
    private StateDao stateDao;
    @EJB(beanName = "MappingUtil")
    private MappingUtil mappingUtil;

    public StateDto getCity(String state) {
        StateEntity stateEntity = stateDao.getStateByName(state);
        return mappingUtil.getDto(stateEntity, StateDto.class);
    }
}
