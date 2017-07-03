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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Yousef
 */
@ManagedBean(name = "itemBean")
@RequestScoped
public class ItemBean implements Serializable {

    @EJB
    private ItemService itemService;
    @Inject
    private ListItemsBean listItems;
    @ManagedProperty(value = "#{user}")
    private UserBean userBean;

    private List<ItemDto> items = new ArrayList<>();
    private List<CategoryDto> categories;
    private String searchString;
    private int categoryId;
    private ItemDto itemDetail;

    public ItemBean() {
    }

    @PostConstruct
    public void init() {
        items = listItems.getItems();

        categories = listItems.getCategories();
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public ItemDto getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(ItemDto itemDetail) {
        this.itemDetail = itemDetail;
    }

    public void doSearchNavBar(int categoryId) {
        this.categoryId = categoryId;
        this.searchString = null;
        doSearch();
    }

    public void doSearchTag(String tagStr) {
        this.categoryId = 0;
        this.searchString = "#" + tagStr;
        doSearch();
    }

    public void doSearchGeneric() {
        this.categoryId = 0;
//        searchString = getUserBean().getGenericSearchString();
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        searchString = ec.getRequestParameterMap().get("searchFormGlobal:searchValue");
//        this.searchString 
        doSearch();
    }

    public void doSearch() {
        try {
            List<ItemDto> items = itemService.searchItems(searchString, categoryId);
            this.items = items;
        } catch (ServiceException ex) {
            Logger.getLogger(SearchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String goToItem(int id) {
        return "itemDetails.xhtml?id=" + id;
    }

    public String addItem() {
        return "addItemForShare.xhtml";
    }

    public InputStream getImage(String filename) {
        InputStream is;
        String filePath = System.getProperty("user.home") + "\\shareit\\images\\sharedItems\\item.png";
        try {
            if (filename != null && !filename.isEmpty()) {
                is = new FileInputStream(new File(System.getProperty("user.home") + filename));

            } else {
                is = new FileInputStream(new File(filePath));
            }
            return is;

        } catch (FileNotFoundException ex) {
            try {
//                String filePath = System.getProperty("user.home") + "\\shareit\\images\\sharedItems\\item.png";
                return new FileInputStream(new File(filePath));
            } catch (FileNotFoundException ex1) {
                return null;
            }
        }
    }

    /**
     * @return the userBean
     */
    public UserBean getUserBean() {
        return userBean;
    }

    /**
     * @param userBean the userBean to set
     */
    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

}
