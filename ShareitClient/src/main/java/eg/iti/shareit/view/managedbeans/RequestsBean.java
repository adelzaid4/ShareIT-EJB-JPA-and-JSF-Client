/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.view.managedbeans;

import eg.iti.shareit.common.Exception.ServiceException;
import eg.iti.shareit.model.dto.ActivityDto;
import eg.iti.shareit.model.dto.UserDto;
import eg.iti.shareit.service.ActivityService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.CollectionDataModel;
import javax.faces.model.DataModel;
import javax.inject.Named;

/**
 *
 * @author Adel Zaid
 */
@Named(value = "requests")
@javax.faces.view.ViewScoped
public class RequestsBean implements Serializable {

    @EJB
    private ActivityService activityService;
    private List<ActivityDto> otherActivityDtos;
    private DataModel<ActivityDto> pendingRequestsdataModel;
    private List<ActivityDto> pendingActivityDtos;

    public RequestsBean() {
        pendingActivityDtos = new ArrayList<>();
    }

    public ActivityService getActivityService() {
        return activityService;
    }

    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }

    public DataModel<ActivityDto> getDataModel() {
        return pendingRequestsdataModel;
    }

    public void setDataModel(DataModel<ActivityDto> dataModel) {
        this.pendingRequestsdataModel = dataModel;
    }

    public DataModel<ActivityDto> getPendingRequestsdataModel() {
        return pendingRequestsdataModel;
    }

    public void setPendingRequestsdataModel(DataModel<ActivityDto> pendingRequestsdataModel) {
        this.pendingRequestsdataModel = pendingRequestsdataModel;
    }

    public List<ActivityDto> getPendingActivityDtos() {
        return pendingActivityDtos;
    }

    public void setPendingActivityDtos(List<ActivityDto> pendingActivityDtos) {
        this.pendingActivityDtos = pendingActivityDtos;
    }

    @PostConstruct
    public void init() {
        try {
            UserDto user = SessionUtil.getUser();
            if (user != null) {
                pendingActivityDtos = activityService.getPendingActivities(user);
                if (pendingActivityDtos != null) {
                    pendingRequestsdataModel = new CollectionDataModel<>(pendingActivityDtos);
                }
                otherActivityDtos = activityService.getOtherActivities(user);
            }
        } catch (ServiceException ex) {
            Logger.getLogger(RequestsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void acceptRequest() {
        try {
            ActivityDto rowData = pendingRequestsdataModel.getRowData();
            activityService.acceptRequest(rowData);
            init();
        } catch (ServiceException ex) {
            Logger.getLogger(RequestsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void declineRequest() {
        try {

            ActivityDto activityDto = pendingRequestsdataModel.getRowData();
            activityService.declineRequest((activityDto.getId()).intValue());
            init();
        } catch (ServiceException ex) {
            Logger.getLogger(RequestsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ActivityDto> getOtherActivityDtos() {
        return otherActivityDtos;
    }

    public void setOtherActivityDtos(List<ActivityDto> otherActivityDtos) {
        this.otherActivityDtos = otherActivityDtos;
    }

    public InputStream getImage(String filename) throws FileNotFoundException {
        return new FileInputStream(new File(System.getProperty("user.home") +filename));
    }

}
