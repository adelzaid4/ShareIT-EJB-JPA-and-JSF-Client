/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.dao;

import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.common.dao.GenericDao;
import eg.iti.shareit.model.entity.NotificationEntity;
import eg.iti.shareit.model.entity.UserEntity;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Adel Zaid
 */
public interface NotificationDao extends GenericDao<NotificationEntity> {

    List<NotificationEntity> getNotSeenNotifications(UserEntity userEntity) throws DatabaseRollbackException;

    List<NotificationEntity> getSeenNotifications(UserEntity userEntity) throws DatabaseRollbackException;

    void saveNotification(NotificationEntity notificationEntity) throws DatabaseRollbackException;

    void setNotificationAsRead(BigDecimal id) throws DatabaseRollbackException;

    int getNotificationNumber(BigDecimal userId) throws DatabaseRollbackException;

}
