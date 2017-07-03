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
import eg.iti.shareit.model.dto.GenderDto;
import eg.iti.shareit.model.dto.ItemDto;
import eg.iti.shareit.model.dto.StateDto;
import eg.iti.shareit.model.dto.UserDto;
import eg.iti.shareit.model.util.HashingUtil;
import eg.iti.shareit.model.util.ImageUtil;
import eg.iti.shareit.service.AddressService;
import eg.iti.shareit.service.UserService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import javax.faces.context.ExternalContext;

import javax.servlet.http.HttpServletRequest;

import javax.faces.context.FacesContext;
import javax.inject.Named;

import javax.servlet.http.Part;

/**
 *
 * @author sara metwalli
 */
@ManagedBean(name = "userManagedBean")
@RequestScoped
public class UserManagedBean implements Serializable {

    private UserDto userDto;
    private UserDto user2;
    private String username;
    private String password;

    private String email;
    private String gender;
    private String image_url;
    private String confirmPassword;
    private AddressDto address;
    private CountryDto country;
    private CityDto city;
    private StateDto state;
    private Part file;
    private int id;
    private int points;
    private boolean canEdit = false;

    private List<ItemDto> items;

    @EJB
    UserService userService;

    @EJB
    private HashingUtil hashingUtil;

    @EJB
    AddressService addressService;

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public CountryDto getCountry() {
        return country;
    }

    public void setCountry(CountryDto country) {
        this.country = country;
    }

    public CityDto getCity() {
        return city;
    }

    public void setCity(CityDto city) {
        this.city = city;
    }

    public StateDto getState() {
        return state;
    }

    public void setState(StateDto state) {
        this.state = state;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public HashingUtil getHashingUtil() {
        return hashingUtil;
    }

    public void setHashingUtil(HashingUtil hashingUtil) {
        this.hashingUtil = hashingUtil;
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public boolean getCanEdit() {
        return canEdit;
    }

    @PostConstruct
    public void init() {
        {
            try {
                userDto = SessionUtil.getUser();
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                if (request.getParameter("id") != null) {
                    id = Integer.parseInt(request.getParameter("id"));
                    UserBean.currentItemId = id;
                } else {
                    id = UserBean.currentItemId;
                    id = userDto.getId().intValue();
                }

                user2 = userService.findUser(BigDecimal.valueOf((long) id));

                if (userDto.getEmail().equals(user2.getEmail())) {
                    canEdit = true;
                }
                username = user2.getUsername();
                email = user2.getEmail();
                gender = user2.getGender().getGender();
                image_url = user2.getImageUrl();
                address = user2.getAddress();
                points = user2.getPoints();
                country = user2.getAddress().getCountry();
                city = user2.getAddress().getCity();
                state = user2.getAddress().getState();
                items = user2.getItems();
                if (items != null) {
                }
            } catch (ServiceException ex) {
                Logger.getLogger(UserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void save() {

        //System.getProperty("user.home") +
        image_url = ImageUtil.SaveImage(file, "\\shareit\\images\\userProfile\\");
        userDto.setImageUrl(image_url);

    }

    public void editUser() {
        if (password.equals(confirmPassword)) {
            try {
                userDto.setUsername(username);
                userDto.setEmail(email);
                GenderDto genderDto = new GenderDto();
                genderDto.setGender(gender);
                userDto.setGender(genderDto);
                AddressDto addressDto = new AddressDto();
                addressDto.setCountry(country);
                addressDto.setState(state);
                addressDto.setCity(city);
                userDto.setAddress(addressDto);
                userDto.setPassword(hashingUtil.getHashedPassword(password));
                // userDto.setImageUrl(image_url);
                userService.updateUser(userDto);
                SessionUtil.getSession().setAttribute("userDto", userDto);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ExternalContext extContext = ctx.getExternalContext();
                String url1 = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/pages/Profile.xhtml?id=" + userDto.getId()));
                try {
                    extContext.redirect(url1);
                } catch (IOException ioe) {
                    throw new FacesException(ioe);
                }
            } catch (ServiceException ex) {
                Logger.getLogger(UserManagedBean.class.getName()).log(Level.SEVERE, null, ex);

            }

        } else {

            FacesMessage facesMessage = new FacesMessage("Error", "passwords doesnot match ");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("form:confirm_password", facesMessage);

        }

    }

    public InputStream getImage(String filename) throws FileNotFoundException {
        InputStream is;
        try {
            is = new FileInputStream(new File(System.getProperty("user.home") + filename));

            return is;
        } catch (FileNotFoundException ex) {
            try {
                String filePath = System.getProperty("user.home") + "\\shareit\\images\\userProfile\\profile.png";
                return new FileInputStream(new File(filePath));
            } catch (FileNotFoundException ex1) {
                return null;
            }
        }
    }

    public String viewUser(String myEmail) {
        try {

            user2 = userService.getUserByEmail(myEmail);
            canEdit = false;
            username = user2.getUsername();
            return "Profile.xhtml";
        } catch (ServiceException ex) {
            Logger.getLogger(UserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String goToItem(int id) {
        return "itemDetails.xhtml?id=" + id;
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

    /**
     * @return the user2
     */
    public UserDto getUser2() {
        return user2;
    }

    /**
     * @param user2 the user2 to set
     */
    public void setUser2(UserDto user2) {
        this.user2 = user2;

    }

}
