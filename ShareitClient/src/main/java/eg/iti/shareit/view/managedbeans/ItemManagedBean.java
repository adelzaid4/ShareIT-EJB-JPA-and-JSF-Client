/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.view.managedbeans;

import eg.iti.shareit.common.Exception.ServiceException;
import eg.iti.shareit.model.dto.CategoryDto;
import eg.iti.shareit.model.dto.ItemDto;
import eg.iti.shareit.model.dto.UserDto;
import eg.iti.shareit.model.util.ImageUtil;
import eg.iti.shareit.model.util.MappingUtil;
import eg.iti.shareit.service.CategoryService;
import eg.iti.shareit.service.ItemService;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Part;

/**
 *
 * @author sara metwalli
 */
@ManagedBean(name = "itemManagedBean", eager = true)
@ViewScoped
public class ItemManagedBean implements java.io.Serializable {

    @EJB
    CategoryService categoryService;
    @EJB
    ItemService itemService;
    @EJB(beanName = "MappingUtil")
    private MappingUtil mappingUtil;

    @Inject
    private ListItemsBean itemsList;
    private String name;
    private String description;
    private Date publish_date;
    private int points;
    private String image_url = "";
    private Part file;
    private CategoryDto category;
    private List<CategoryDto> categories;
    private String tags;

    public ItemManagedBean(CategoryService categoryService, String name, String description, Date publish_date, int points, String image_url) {
        this.categoryService = categoryService;
        this.name = name;
        this.description = description;
        this.publish_date = publish_date;
        this.points = points;
        this.image_url = image_url;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public ItemManagedBean() {
    }

    @PostConstruct
    public void init() {
        try {
            categories = categoryService.getAllCategories();
            categories = categoryService.getAllCategories();
        } catch (ServiceException ex) {
            Logger.getLogger(ItemManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String test() {

        return "register";

    }

    public void addItem() throws ServiceException {
        int pts = category.getMaxPoints();
        if (points <= pts) {
            if (points > 0) {

                ItemDto item = new ItemDto(name, description, 1, new Date(), points, image_url, tags, SessionUtil.getUser());
                item.setImageUrl(image_url);
                item.setCategory(category);

                itemService.addItemForShare(item);
                itemsList.setItems(itemService.getAllItems());
                UserDto user = (UserDto) SessionUtil.getUser();
                user.getItems().add(item);
                SessionUtil.getSession().setAttribute("userDto", user);
                clear();
                FacesContext ctx = FacesContext.getCurrentInstance();
                ExternalContext extContext = ctx.getExternalContext();
                String url1 = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/pages/items.xhtml"));
                try {
                    extContext.redirect(url1);
                } catch (IOException ioe) {
                    throw new FacesException(ioe);
                }
            } else {
                FacesMessage facesMessage = new FacesMessage("Error", " points cannot be less than 1 point ");
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.addMessage("addItemForm:ptsErr", facesMessage);

            }
        } else {
            FacesMessage facesMessage = new FacesMessage("Error", "these points exceeded the max no of points allowed ");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("addItemForm:ptsErr", facesMessage);

        }

    }

    public void save() {
        // System.getProperty("user.home") 
        image_url = ImageUtil.SaveImage(file, "\\shareit\\images\\sharedItems\\");
    }

    public void clear() {
        name = "";
        description = "";
        points = 0;
        tags = "";
    }

}
