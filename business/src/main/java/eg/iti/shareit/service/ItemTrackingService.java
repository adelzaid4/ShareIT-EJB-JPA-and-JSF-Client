/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.service;

import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.common.Exception.ServiceException;
import eg.iti.shareit.model.dao.ActivityDao;
import eg.iti.shareit.model.dao.BorrowStateDao;
import eg.iti.shareit.model.dto.BorrowStateDto;
import eg.iti.shareit.model.dto.UserDto;
import eg.iti.shareit.model.entity.BorrowStateEntity;
import eg.iti.shareit.model.entity.UserEntity;
import eg.iti.shareit.model.util.MappingUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adel Zaid
 */
@Stateless
public class ItemTrackingService {

    private static final Logger logger = Logger.getLogger(ActivityService.class.getName());
    @EJB
    private BorrowStateDao borrowStateDao;
    @EJB(beanName = "MappingUtil")
    private MappingUtil mappingUtil;

    public void handleBorrowingDueDate(UserDto userDto) throws ServiceException {
        try {
            UserEntity userEntity = mappingUtil.getEntity(userDto, UserEntity.class);
            borrowStateDao.handleBorrowingDueDate(userEntity);
        } catch (DatabaseRollbackException ex) {
            Logger.getLogger(ItemTrackingService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public List<BorrowStateDto> getBorrowStatus(UserDto userDto) throws ServiceException {
        try {
            UserEntity userEntity = mappingUtil.getEntity(userDto, UserEntity.class);
            List<BorrowStateDto> dtoList = mappingUtil.<BorrowStateEntity, BorrowStateDto>getDtoList(borrowStateDao.getBorrowStatus(userEntity), BorrowStateDto.class);
            return dtoList;
        } catch (DatabaseRollbackException e) {
            Logger.getLogger(ItemTrackingService.class.getName()).log(Level.SEVERE, null, e);
            throw new ServiceException(e.getMessage());
        }
    }

    public void updateBorrowStatus(BorrowStateDto borrowStateDto) throws ServiceException {
        try {
            borrowStateDto.setIsBack(BigInteger.valueOf(1));
            BorrowStateEntity borrowStateEntity = mappingUtil.getEntity(borrowStateDto, BorrowStateEntity.class);
            borrowStateDao.updateBorrowStatus(borrowStateEntity);
        } catch (DatabaseRollbackException ex) {
            Logger.getLogger(ItemTrackingService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public int getItemStatusNumber(BigDecimal userID) throws ServiceException {
        try {
            return borrowStateDao.getItemStatusNumber(userID);
        } catch (DatabaseRollbackException ex) {
            Logger.getLogger(ItemTrackingService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
