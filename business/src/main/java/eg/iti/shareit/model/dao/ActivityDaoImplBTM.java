/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.dao;

import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.common.enums.StatusEnum;
import eg.iti.shareit.model.dto.ActivityDto;
import eg.iti.shareit.model.entity.ActivityEntity;
import eg.iti.shareit.model.entity.ItemEntity;
import eg.iti.shareit.model.entity.NotificationEntity;
import eg.iti.shareit.model.entity.UserEntity;
import eg.iti.shareit.model.util.MappingUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Adel Zaid
 */
@Stateless(mappedName = "ActivityDaoImplBTM")
@TransactionManagement(TransactionManagementType.BEAN)
public class ActivityDaoImplBTM extends GenericDaoImpl<ActivityEntity> implements ActivityDaoBTM {

    public ActivityDaoImplBTM() {
        super(ActivityEntity.class);
    }

    @Inject
    UserTransaction userTransaction;
    @Inject
    UserDao userDao;
    @Inject
    ActivityDao activityDao;
    @EJB
    NotificationDao notificationDao;
    @Inject
    ItemDao itemDao;
    @EJB(beanName = "MappingUtil")
    private MappingUtil mappingUtil;

    @Override
    public void acceptRequest(ActivityEntity activityEntity) throws DatabaseRollbackException {
        try {
            userTransaction.begin();

            //make the status accepted for the request.
            activityEntity.setStatus(StatusEnum.ACCEPTED.getStatus());
            activityDao.update(activityEntity);

            //We need to get the points from hamada and transfer it to mervat
            //So I get the points of the item because if hamada doesn't have enough points he'll not be able to make the request.
            int fromPoints = activityEntity.getItem().getPoints().intValue();
            int toPoints = activityEntity.getToUser().getPoints().intValue() + fromPoints;
            UserEntity mervatEntity = activityEntity.getToUser();
            mervatEntity.setPoints(new BigInteger(String.valueOf(toPoints)));
            userDao.update(mervatEntity);

            //We need to deduct points from hamada's account
            UserEntity hamadaEntity = activityEntity.getFromUser();
            BigInteger pointsDeducted = new BigInteger(String.valueOf(hamadaEntity.getPoints().intValue() - fromPoints));
            hamadaEntity.setPoints(pointsDeducted);
            userDao.update(hamadaEntity);

            //Lock the item by making the is_available=0 which means unavailable
            activityEntity.getItem().setIsAvailable((short) 0);
            ItemEntity itemEntity = activityEntity.getItem();
            itemDao.update(itemEntity);

            //We need to decline any other request of the same item to the same user
            List<ActivityEntity> pendingActivities = activityDao.getPendingActivities(activityEntity.getToUser());
            for (ActivityEntity pendingActivity : pendingActivities) {
                if (pendingActivity.getItem().getId().intValue() == activityEntity.getItem().getId().intValue()) {
                    pendingActivity.setStatus(StatusEnum.DECLINED.getStatus());
                }
                activityDao.update(pendingActivity);
            }
            //Form the entity of notification
            NotificationEntity notificationEntity = new NotificationEntity();
            notificationEntity.setItem(activityEntity.getItem());
            notificationEntity.setFromUser(activityEntity.getFromUser());
            notificationEntity.setToUser(activityEntity.getToUser());
            notificationEntity.setMeetingPoint(activityEntity.getMeetingPoint());
            ActivityDto activityDto = mappingUtil.getDto(activityEntity, ActivityDto.class);
            notificationEntity.setDays(BigInteger.valueOf((long) (activityDto.calculateIntervalOfTime())));
            notificationEntity.setPointsDeducted(pointsDeducted);
            notificationEntity.setSeen(BigInteger.valueOf(0));
            notificationDao.saveNotification(notificationEntity);

            userTransaction.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                Logger.getLogger(ActivityDaoImplBTM.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw new DatabaseRollbackException("Cannot save this activity");
        }
    }

}
