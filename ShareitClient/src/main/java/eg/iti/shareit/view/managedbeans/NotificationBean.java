/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.view.managedbeans;

import eg.iti.shareit.common.Exception.ServiceException;
import eg.iti.shareit.model.dto.ActivityDto;
import eg.iti.shareit.model.dto.NotificationDto;
import eg.iti.shareit.model.dto.UserDto;
import eg.iti.shareit.service.NotificationService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.CollectionDataModel;
import javax.faces.model.DataModel;
import javax.inject.Named;

/**
 *
 * @author Adel Zaid
 */
@ManagedBean(name = "notificationBean")
@RequestScoped
public class NotificationBean implements Serializable {

    @EJB
    private NotificationService notificationService;
    private List<NotificationDto> notSeenNotificationDtos;
    private DataModel<NotificationDto> notSeenDataModel;
    private List<NotificationDto> seenNotificationDtos;

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public List<NotificationDto> getNotSeenNotificationDtos() {
        return notSeenNotificationDtos;
    }

    public void setNotSeenNotificationDtos(List<NotificationDto> notSeenNotificationDtos) {
        this.notSeenNotificationDtos = notSeenNotificationDtos;
    }

    public DataModel<NotificationDto> getNotSeenDataModel() {
        return notSeenDataModel;
    }

    public void setNotSeenDataModel(DataModel<NotificationDto> notSeenDataModel) {
        this.notSeenDataModel = notSeenDataModel;
    }

    public List<NotificationDto> getSeenNotificationDtos() {
        return seenNotificationDtos;
    }

    public void setSeenNotificationDtos(List<NotificationDto> seenNotificationDtos) {
        this.seenNotificationDtos = seenNotificationDtos;
    }

    @PostConstruct
    public void init() {
        try {
            UserDto user = SessionUtil.getUser();
            if (user != null) {
                notSeenNotificationDtos = notificationService.getNotSeenNotifications(user);
                if (notSeenNotificationDtos != null) {
                    notSeenDataModel = new CollectionDataModel<>(notSeenNotificationDtos);
                }
                seenNotificationDtos = notificationService.getSeenNotifications(user);
            }
        } catch (ServiceException ex) {
            Logger.getLogger(NotificationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setNotificationAsRead() {
        try {
            NotificationDto notificationDto = notSeenDataModel.getRowData();
            notificationService.setNotificationAsRead(notificationDto.getId());
            init();
        } catch (ServiceException ex) {
            Logger.getLogger(NotificationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InputStream getImage(String filename) throws FileNotFoundException {
        return new FileInputStream(new File(System.getProperty("user.home") +filename));
    }

}
