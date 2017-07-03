/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.service;

import eg.iti.shareit.common.Exception.DatabaseException;
import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.common.Exception.ServiceException;
import eg.iti.shareit.model.dao.ActivityDao;
import eg.iti.shareit.model.dao.NotificationDao;
import eg.iti.shareit.model.dto.NotificationDto;
import eg.iti.shareit.model.dto.UserDto;
import eg.iti.shareit.model.entity.NotificationEntity;
import eg.iti.shareit.model.entity.UserEntity;
import eg.iti.shareit.model.util.MappingUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adel Zaid
 */
@Stateless
public class NotificationService {

    private static final Logger logger = Logger.getLogger(ActivityService.class.getName());
    @EJB
    private NotificationDao notificationDao;
    @EJB(beanName = "MappingUtil")
    private MappingUtil mappingUtil;

    public List<NotificationDto> getNotSeenNotifications(UserDto userDto) throws ServiceException {
        try {
            UserEntity userEntity = mappingUtil.getEntity(userDto, UserEntity.class);
            List<NotificationEntity> notificationEntities = notificationDao.getNotSeenNotifications(userEntity);
            List<NotificationDto> notificationDtos = mappingUtil.<NotificationEntity, NotificationDto>getDtoList(notificationEntities, NotificationDto.class);
            return notificationDtos;
        } catch (DatabaseException ex) {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public List<NotificationDto> getSeenNotifications(UserDto userDto) throws ServiceException {
        try {
            UserEntity userEntity = mappingUtil.getEntity(userDto, UserEntity.class);
            List<NotificationEntity> notificationEntities = notificationDao.getSeenNotifications(userEntity);
            List<NotificationDto> notificationDtos = mappingUtil.<NotificationEntity, NotificationDto>getDtoList(notificationEntities, NotificationDto.class);
            return notificationDtos;
        } catch (DatabaseException ex) {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public void setNotificationAsRead(BigDecimal id) throws ServiceException {

        try {
            notificationDao.setNotificationAsRead(id);
        } catch (DatabaseException ex) {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public int getNotificationNumber(BigDecimal userId) throws ServiceException {
        try {
            return notificationDao.getNotificationNumber(userId);
        } catch (DatabaseRollbackException ex) {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
