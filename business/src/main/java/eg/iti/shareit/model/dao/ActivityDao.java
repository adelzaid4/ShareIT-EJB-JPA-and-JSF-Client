/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.dao;

import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.common.dao.GenericDao;
import eg.iti.shareit.model.dto.UserDto;
import eg.iti.shareit.model.entity.ActivityEntity;

import eg.iti.shareit.model.entity.UserEntity;
import java.util.List;

/**
 *
 * @author Adel Zaid
 */
public interface ActivityDao extends GenericDao<ActivityEntity> {

    public void saveActivity(ActivityEntity activityEntity) throws DatabaseRollbackException;

    public String cancelRequest(int id) throws DatabaseRollbackException;

    public String declineRequest(int id) throws DatabaseRollbackException;

    public List<ActivityEntity> getPendingActivities(UserEntity userEntity) throws DatabaseRollbackException;

    public List<ActivityEntity> getOtherActivities(UserEntity userEntity) throws DatabaseRollbackException;

    public ActivityEntity getMyActivityOfItem(int itemId, int userId) throws DatabaseRollbackException;

    //This method for getting the activities which belongs to user and he accepted it
    public List<ActivityEntity> getAvtivityOfUser(UserEntity userEntity) throws DatabaseRollbackException;
}
