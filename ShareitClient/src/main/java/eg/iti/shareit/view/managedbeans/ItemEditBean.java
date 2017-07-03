/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.view.managedbeans;

import eg.iti.shareit.common.Exception.ServiceException;
import eg.iti.shareit.model.dto.CategoryDto;
import eg.iti.shareit.model.dto.ItemDto;
import eg.iti.shareit.model.util.ImageUtil;
import eg.iti.shareit.service.CategoryService;
import eg.iti.shareit.service.ItemService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.primefaces.context.RequestContext;

/**
 *
 * @author El-Greatly
 */
@ManagedBean(name = "itemEditBean")
@ViewScoped
public class ItemEditBean implements Serializable {

    private ItemDto item;

    private Part file;
    private List<CategoryDto> categories = new ArrayList<>();

    @EJB
    private ItemService itemService;

    @EJB
    private CategoryService categoryService;

    @Inject
    private ListItemsBean itemsList;

    private int points;

    public ItemEditBean() {
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public ItemDto getItem() {
        return item;
    }

    public void setItem(ItemDto item) {
        this.item = item;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    @PostConstruct
    public void init() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            int id = Integer.parseInt(request.getParameter("id"));

            item = itemService.getItemById(id);
            points = item.getPoints();
            categories = categoryService.getAllCategories();

        } catch (ServiceException ex) {
            Logger.getLogger(ItemEditBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save() {
        //System.getProperty("user.home") 
        String image_url = ImageUtil.SaveImage(file, "\\shareit\\images\\sharedItems\\");
        item.setImageUrl(image_url);
    }

    public void updateItem() {
        try {
            int pnts = item.getCategory().getMaxPoints();
            item.setPoints(item.getPoints());
            if (item.getPoints() <= pnts) {
                if (item.getPoints() > 0) {
                    itemService.updateSharedItem(item);
                    FacesContext context = FacesContext.getCurrentInstance();
//            context.addMessage(null, new FacesMessage("Successful", "Item is updated Successfully"));

                    ExternalContext extContext = context.getExternalContext();
                    String url1 = extContext.encodeActionURL(context.getApplication().getViewHandler().getActionURL(context, "/pages/itemDetails.xhtml"));
                    extContext.redirect(url1);
                    itemsList.setItems(itemService.getAllItems());
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
        } catch (ServiceException | IOException ex) {
            Logger.getLogger(ItemDetailBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InputStream getImage(String filename) throws FileNotFoundException {
        return new FileInputStream(new File(System.getProperty("user.home") + filename));
    }

}
