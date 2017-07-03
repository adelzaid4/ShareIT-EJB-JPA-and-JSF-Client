/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.view.managedbeans;

import eg.iti.shareit.common.Exception.ServiceException;
import eg.iti.shareit.model.dto.CategoryDto;
import eg.iti.shareit.service.CategoryService;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author El-Greatly
 */
@ManagedBean(name = "category")
@ApplicationScoped
public class CategoryBean implements Serializable {

    @EJB
    private CategoryService categoryService;
    @Inject
    private ListItemsBean listItems;
    private List<CategoryDto> categories;

    private List<CategoryDto> firstList;

    private List<CategoryDto> secondList;

    private int categoryListSize;

    private int firstListSize, secondListSize;

    public List<CategoryDto> getFirstList() {
        return firstList;
    }

    public void setFirstList(List<CategoryDto> firstList) {
        this.firstList = firstList;
    }

    public List<CategoryDto> getSecondList() {
        return secondList;
    }

    public void setSecondList(List<CategoryDto> secondList) {
        this.secondList = secondList;
    }

    public int getCategoryListSize() {
        return categoryListSize;
    }

    public void setCategoryListSize(int categoryListSize) {
        this.categoryListSize = categoryListSize;
    }

    public int getFirstListSize() {
        return firstListSize;
    }

    public void setFirstListSize(int firstListSize) {
        this.firstListSize = firstListSize;
    }

    public int getSecondListSize() {
        return secondListSize;
    }

    public void setSecondListSize(int secondListSize) {
        this.secondListSize = secondListSize;
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

    @PostConstruct
    public void init() {
        firstList = new ArrayList<>();
        secondList = new ArrayList<>();
        categories = listItems.getCategories();
        categoryListSize = categories.size();
        if (categoryListSize % 2 == 0) {
            firstListSize = categoryListSize / 2;
            secondListSize = categoryListSize - firstListSize;
        } else {
            categoryListSize += 1;
            firstListSize = categoryListSize / 2;
            secondListSize = categoryListSize - firstListSize;
        }
        for (int first = 0; first < firstListSize; first++) {
            firstList.add(categories.get(first));
        }
        for (int second = firstListSize; second < categories.size(); second++) {
            secondList.add(categories.get(second));
        }
    }

}
