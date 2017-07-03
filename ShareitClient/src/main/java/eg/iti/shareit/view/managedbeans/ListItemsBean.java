/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.view.managedbeans;

import eg.iti.shareit.common.Exception.ServiceException;
import eg.iti.shareit.model.dto.CategoryDto;
import eg.iti.shareit.model.dto.ItemDto;
import eg.iti.shareit.service.ItemService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Yousef
 */
@Named(value = "itemsList")
@ApplicationScoped
public class ListItemsBean {

    /**
     * Creates a new instance of ListItemsBean
     */
    private List<ItemDto> items;
    private List<CategoryDto> categories;
    @EJB
    private ItemService itemService;

    public ListItemsBean() {
    }

    @PostConstruct
    public void init() {
        try {
            if (itemService.getAllItems() != null) {
                items = itemService.getAllItems();
            }
            categories = itemService.getAllCategories();
        } catch (ServiceException ex) {
            Logger.getLogger(RequestsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

}
