/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.dao;

import eg.iti.shareit.common.dao.GenericDao;
import eg.iti.shareit.model.entity.GenderEntity;

/**
 *
 * @author Yousef
 */
public interface GenderDao extends GenericDao<GenderEntity> {
    public GenderEntity getGenderByName(String gender);
}
