package eg.iti.shareit.service;

import eg.iti.shareit.common.Exception.DatabaseException;
import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.common.Exception.ServiceException;
import eg.iti.shareit.model.dao.UserDao;
import eg.iti.shareit.model.dto.AddressDto;
import eg.iti.shareit.model.dto.UserDto;
import eg.iti.shareit.model.entity.AddressEntity;
import eg.iti.shareit.model.entity.UserEntity;
import eg.iti.shareit.model.util.MappingUtil;
import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Mohamed_2 on 11/14/2015.
 */
@Stateless
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    @EJB
    private UserDao userDao;
    @EJB
    private GenderService genderService;
    @EJB
    private AddressService addressService;
    @EJB(beanName = "MappingUtil")
    private MappingUtil mappingUtil;

    public UserDto getUserByEmail(String email) throws ServiceException {
        try {
            UserEntity userEntity = userDao.getUserByEmail(email);
            if (userEntity != null) {
                UserDto userDto = mappingUtil.getDto(userEntity, UserDto.class);
                return userDto;
            } else {
                return null;
            }
        } catch (DatabaseRollbackException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

    public void RegisterUser(UserDto user) throws ServiceException {
        try {
            user.setGender(genderService.getGender(user.getGender().getGender()));
            AddressDto addressDto = addressService.getAddress(user.getAddress());
            user.setAddress(addressDto);
            UserEntity userEntity = mappingUtil.getEntity(user, UserEntity.class);
            //AddressEntity addressEntity = mappingUtil.getEntity(addressDto, AddressEntity.class);
            //("addressEntity is "+addressEntity);
            //userEntity.setAddress(addressEntity);
            boolean saved = userDao.saveUser(userEntity);
        } catch (DatabaseRollbackException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

    public void updateUser(UserDto user) throws ServiceException {
        try {
            user.setGender(genderService.getGender(user.getGender().getGender()));
            AddressDto addressDto = addressService.getAddress(user.getAddress());
            user.setAddress(addressDto);
            UserEntity userEntity = mappingUtil.getEntity(user, UserEntity.class);
            boolean added = userDao.updateUser(userEntity);

        } catch (DatabaseRollbackException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

    public UserDto findUser(String email, String password) throws ServiceException {
        try {
            UserEntity userEntity = userDao.findUser(email, password);
            if (userEntity != null) {
                UserDto userDto = mappingUtil.getDto(userEntity, UserDto.class);
                //userDto.setAddressDto(mappingUtil.getDto(userEntity.getAddress(), AddressDto.class));
                return userDto;
            }
            return null;
        } catch (DatabaseRollbackException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

    public UserDto findUser(BigDecimal id) throws ServiceException {
        try {
            UserEntity userEntity = userDao.getUserById(id);
            if (userEntity != null) {
                UserDto userDto = mappingUtil.getDto(userEntity, UserDto.class);
                //userDto.setAddressDto(mappingUtil.getDto(userEntity.getAddress(), AddressDto.class));
                return userDto;
            }
            return null;
        } catch (DatabaseRollbackException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }

    }
}
