/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.view.managedbeans;

import eg.iti.shareit.common.Exception.ServiceException;
import eg.iti.shareit.model.dto.ActivityDto;
import eg.iti.shareit.model.dto.CategoryDto;
import eg.iti.shareit.model.dto.ItemDto;
import eg.iti.shareit.service.ActivityService;
import eg.iti.shareit.service.CategoryService;
import eg.iti.shareit.service.ItemService;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author Yousef
 */
@Named(value = "itemDetailBean")
@RequestScoped
public class ItemDetailBean implements Serializable {

    /**
     * Creates a new instance of ItemDetailBean
     */
    private ItemDto item;
    @EJB
    private ActivityService activityService;

    @EJB
    private ItemService itemService;

    @EJB
    private CategoryService categoryService;

    @Inject
    private ItemBean itemBean;

    @Inject
    private ListItemsBean listItemsBean;

    @Inject
    private UserBean user;

    private int id;
    private long publishDays;

    private String timeFrom;
    private String timeTo;
    private String meetingPoint;
    private boolean isRequested;
    private ActivityDto activity;
    private boolean noRequest;
    private List<ItemDto> relatedItems;
    private String message;
    private String todayString;
    private String url;

    private CategoryDto category;
    private String description;
    private String imageUrl;
    private int isAvailabe;
    private String name;
    private int points;
    private String tags;
    private Part file;
    private List<String> hashTags;

    @PostConstruct
    public void init() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (request.getParameter("id") != null) {
                id = Integer.parseInt(request.getParameter("id"));
                UserBean.currentItemId = id;
            } else {
                id = UserBean.currentItemId;
            }

            for (ItemDto currentItem : listItemsBean.getItems()) {
                if (currentItem.getId().intValue() == id) {
                    item = currentItem;
                    break;
                }
            }
            if (item == null) {
                item = itemService.getItemById(id);
            }

            hashTags = Arrays.asList(item.getTags().split(","));

            Date date1 = new Date();
            Date date2 = item.getPublishDate();
            long diff = date1.getTime() - date2.getTime();
            publishDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            if (user.getUserDto() != null) {
                activity = activityService.getActivityOfMyItem(item.getId().intValue(), user.getUserDto().getId().intValue());
            }
            if (activity != null) {
                isRequested = true;
            }
            if (item.getIsAvailable() == 0) {
                message = "Item is not available";
                noRequest = true;
            }

            if (user.getUserDto() != null && user.getUserDto().getPoints() < item.getPoints()) {
                message = "You don't have enough points";
                noRequest = true;
            }

            relatedItems = itemService.getRelatedItems(item);
            todayString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        } catch (ServiceException ex) {
            Logger.getLogger(ItemDetailBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ListItemsBean getListItemsBean() {
        return listItemsBean;
    }

    public void setListItemsBean(ListItemsBean listItemsBean) {
        this.listItemsBean = listItemsBean;
    }

    public ItemBean getItemBean() {
        return itemBean;
    }

    public void setItemBean(ItemBean itemBean) {
        this.itemBean = itemBean;
    }

    public List<String> getHashTags() {
        return hashTags;
    }

    public void setHashTags(List<String> hashTags) {
        this.hashTags = hashTags;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public ActivityService getActivityService() {
        return activityService;
    }

    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getIsAvailabe() {
        return isAvailabe;
    }

    public void setIsAvailabe(int isAvailabe) {
        this.isAvailabe = isAvailabe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTodayString() {
        return todayString;
    }

    public void setTodayString(String todayString) {
        this.todayString = todayString;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ItemDto> getRelatedItems() {
        return relatedItems;
    }

    public void setRelatedItems(List<ItemDto> relatedItems) {
        this.relatedItems = relatedItems;
    }

    public ItemDetailBean() {
    }

    public boolean isNoRequest() {
        return noRequest;
    }

    public void setNoRequest(boolean noRequest) {
        this.noRequest = noRequest;
    }

    public ActivityDto getActivity() {
        return activity;
    }

    public void setActivity(ActivityDto activity) {
        this.activity = activity;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public ItemDto getItem() {
        return item;
    }

    public void setItem(ItemDto item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPublishDays() {
        return publishDays;
    }

    public void setPublishDays(long publishDays) {
        this.publishDays = publishDays;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getMeetingPoint() {
        return meetingPoint;
    }

    public void setMeetingPoint(String meetingPoint) {
        this.meetingPoint = meetingPoint;
    }

    public boolean isIsRequested() {
        return isRequested;
    }

    public void setIsRequested(boolean isRequested) {
        this.isRequested = isRequested;
    }

    public String requestItem() {
        Date timeFromDate, timeToDate, todayDate;
        try {

            timeFromDate = new SimpleDateFormat("yyyy-MM-dd").parse(timeFrom);
            timeToDate = new SimpleDateFormat("yyyy-MM-dd").parse(timeTo);
            todayDate = new SimpleDateFormat("yyyy-MM-dd").parse(todayString);
//
            Calendar calendarFrom = Calendar.getInstance();
            calendarFrom.setTime(timeFromDate);

            Calendar calendarTo = Calendar.getInstance();
            calendarTo.setTime(timeToDate);

            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(todayDate);

            boolean error = false;

            if (calendarFrom.compareTo(calendarNow) < 0) {
                FacesMessage facesMessage = new FacesMessage("From date can't be before today");
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.addMessage("detailForm:timeFrom", facesMessage);

                error = true;
            }

            if (calendarTo.compareTo(calendarFrom) < 0) {
                FacesMessage facesMessage = new FacesMessage("to date must be after from date");
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.addMessage("detailForm:timeTo", facesMessage);

                error = true;
            }

            if (error) {
                return "";
            }

            boolean result = activityService.requestItem(item.getId().intValue(), user.getUserDto().getId().intValue(), item.getUserFrom().getId().intValue(), timeFromDate, timeToDate, meetingPoint);
            isRequested = true;
        } catch (ServiceException ex) {
            Logger.getLogger(ItemDetailBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            FacesMessage facesMessage = new FacesMessage("please provid valid date format dd-MM-yyyy");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("detailForm:timeTo", facesMessage);
            facesContext.addMessage("detailForm:timeFrom", facesMessage);
        }
        return "";
    }

    public void cancelRequest() {
        if (activity != null) {
            try {
                String result = activityService.declineRequest(activity.getId().intValue());
                if (result != null) {
                    isRequested = false;
                }
            } catch (ServiceException ex) {
                Logger.getLogger(ItemDetailBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deleteItem() {

        try {
            if (itemService.isItemAvailable(id)) {
                itemService.deleteSharedItem(item);
                listItemsBean.getItems().remove(item);
                //  itemBean.getItems().remove(item);
                //   addMessage("Successful", " item  deleted");
                FacesContext ctx = FacesContext.getCurrentInstance();
                ExternalContext extContext = ctx.getExternalContext();

                String url1 = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/pages/items.xhtml"));

                try {
                    extContext.redirect(url1);
                } catch (IOException ioe) {
                    throw new FacesException(ioe);
                }

            } else {
//                FacesContext context = FacesContext.getCurrentInstance();
//                context.addMessage(null, new FacesMessage("Error", "you cannot delete the item"));
//                return "";
                addMessage("System Error", "Sorry item cannot be deleted");

            }
        } catch (ServiceException ex) {
            Logger.getLogger(ItemDetailBean.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void validateDateFrom(FacesContext context, UIComponent component, Object value) {

    }

    public String goToEditItem(int id) {
        return "editItem.xhtml?id=" + id;
    }

    public String goToPublisher(BigDecimal id) {
        return "Profile.xhtml?id=" + id;
    }

//    public void validateDateTo(FacesContext context, UIComponent component, Object value){
//        try {
//            Date timeFromDate = new SimpleDateFormat("dd-MM-yyyy").parse((String) value);
//            
//            long diff = date1.getTime() - date2.getTime();
//            publishDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
//        } catch (ParseException ex) {
//            Logger.getLogger(ItemDetailBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
