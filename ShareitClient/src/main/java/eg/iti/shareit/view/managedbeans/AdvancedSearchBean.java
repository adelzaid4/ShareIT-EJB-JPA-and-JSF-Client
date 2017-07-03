/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.view.managedbeans;

import eg.iti.shareit.common.Exception.ServiceException;
import eg.iti.shareit.model.dto.AddressDto;
import eg.iti.shareit.model.dto.CityDto;
import eg.iti.shareit.model.dto.CountryDto;
import eg.iti.shareit.model.dto.ItemDto;
import eg.iti.shareit.model.dto.StateDto;
import eg.iti.shareit.model.dto.UserDto;
import eg.iti.shareit.service.AddressService;
import eg.iti.shareit.service.ItemService;
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
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Yousef
 */
@ManagedBean(name = "AdvancedSearch", eager = true)
@SessionScoped
public class AdvancedSearchBean implements Serializable {

    /**
     * Creates a new instance of SearchBean
     */
    @EJB
    private ItemService itemService;
    private CountryDto country;
    private CityDto city;
    private StateDto state;

    private List<ItemDto> items;

    public String nearBySearch() {
        try {
            UserDto user = SessionUtil.getUser();
            setItems(itemService.searchByLocation(user.getAddress()));
        } catch (ServiceException ex) {
            Logger.getLogger(AdvancedSearchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String advancedSearch() {
        try {
            AddressDto addressDto = new AddressDto();
            addressDto.setCountry(getCountry());
            addressDto.setState(getState());
            addressDto.setCity(getCity());
            setItems(itemService.searchByLocation(addressDto));
            //itemBean.setItems(items);
            return "";
        } catch (ServiceException ex) {
            Logger.getLogger(SearchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     * @return the country
     */
    public CountryDto getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(CountryDto country) {
        this.country = country;
    }

    /**
     * @return the city
     */
    public CityDto getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(CityDto city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public StateDto getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(StateDto state) {
        this.state = state;
    }

    /**
     * @return the items
     */
    public List<ItemDto> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    public String goToItem(int id) {
        return "itemDetails.xhtml?id=" + id;
    }

    public InputStream getImage(String filename) throws FileNotFoundException {
        return new FileInputStream(new File(System.getProperty("user.home") + filename));
    }
}
