/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.dao;

import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.common.enums.StatusEnum;
import eg.iti.shareit.model.entity.ActivityEntity;
import eg.iti.shareit.model.entity.StatusEntity;
import eg.iti.shareit.model.entity.UserEntity;
import eg.iti.shareit.model.util.MappingUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Adel Zaid
 */
@Stateless(mappedName = "ActivityDaoImpl")
public class ActivityDaoImpl extends GenericDaoImpl<ActivityEntity> implements ActivityDao {

    @EJB
    ActivityDao activityDao;
    @Inject
    StatusDao statusDao;
    @EJB(beanName = "MappingUtil")
    private MappingUtil mappingUtil;

    public ActivityDaoImpl() {
        super(ActivityEntity.class);
    }

    @Override
    public void saveActivity(ActivityEntity activityEntity) throws DatabaseRollbackException {
        StatusEntity statusEntitiy = statusDao.get(activityEntity.getStatus().getId());
        if (statusEntitiy == null) {
            activityEntity.getStatus().setId(null);
            statusDao.save(activityEntity.getStatus());
        }
        save(activityEntity);

    }

    @Override
    public String cancelRequest(int id) throws DatabaseRollbackException {
        try {
            //Get object
            //Set Activity Deleted
            ActivityEntity activityEntity = activityDao.get(BigDecimal.valueOf(id));
            if (activityEntity.getActivityDeleted() == 1) {
                return "Deleted";
            } else if (activityEntity.getStatus().getStatus().equals("Pending")) {

                activityEntity.setActivityDeleted((short) 1);
                activityDao.update(activityEntity);
                return "Pending";
            } else if (activityEntity.getStatus().getStatus().equals("Declined")) {
                return "Declined";
            } else {
                return "Accepted";
            }
        } catch (Exception e) {
            Logger.getLogger(ActivityDaoImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseRollbackException("Cannot Cancel The request");
        }
    }

    @Override
    public String declineRequest(int id) throws DatabaseRollbackException {
        try {
            ActivityEntity activityEntity = activityDao.get(BigDecimal.valueOf(id));
            if (activityEntity.getActivityDeleted() == 1) {
                return "Deleted";
            } else if (activityEntity.getStatus().getStatus().equals("Declined")) {
                return "Already Declined";
            } else {

                activityEntity.setStatus(StatusEnum.DECLINED.getStatus());
                activityEntity.setActivityDeleted((short) 1);
                activityDao.update(activityEntity);
                return "Declined";
            }
        } catch (Exception e) {
            Logger.getLogger(ActivityDaoImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseRollbackException("Cannot Cancel The request");
        }
    }

    @Override
    public ActivityEntity getMyActivityOfItem(int itemId, int userId) throws DatabaseRollbackException {
        try {

            Query query = getEntityManager().createQuery("Select a From ActivityEntity a where a.activityDeleted = 0 AND a.status.id = 1 AND a.fromUser.id = " + userId + " AND a.item.id = " + itemId);
            List<ActivityEntity> list = query.getResultList();
            if (list != null && list.size() > 0) {
                ActivityEntity activityEntity = list.get(0);
                return activityEntity;
            } else {
                return null;
            }
        } catch (PersistenceException ex) {
            ex.printStackTrace();
            throw new DatabaseRollbackException(ex.getMessage());
        }
    }

    @Override
    public List<ActivityEntity> getPendingActivities(UserEntity userEntity) throws DatabaseRollbackException {
        Query query = getEntityManager().createQuery("Select a From ActivityEntity a where a.toUser=" + userEntity.getId() + " and a.status.status = 'Pending'");
        List<ActivityEntity> activityEntities = query.getResultList();
        if (activityEntities != null) {
            return activityEntities;
        }
        return null;
    }

    @Override
    public List<ActivityEntity> getOtherActivities(UserEntity userEntity) throws DatabaseRollbackException {
        Query query = getEntityManager().createQuery("Select a From ActivityEntity a where a.toUser.id=" + userEntity.getId() + " and a.status.status != 'Pending'");
        List<ActivityEntity> activityEntities = query.getResultList();
        if (activityEntities != null) {
            return activityEntities;
        }
        return null;
    }

    //This method for getting the activities which belongs to user and he accepted it
    @Override
    public List<ActivityEntity> getAvtivityOfUser(UserEntity userEntity) throws DatabaseRollbackException {
        Query query = getEntityManager().createQuery("Select a From ActivityEntity a where a.toUser.id=" + userEntity.getId() + " and a.status.status = 'Accepted'");
        List<ActivityEntity> activityEntities = query.getResultList();
        if (activityEntities != null) {
            return activityEntities;
        }
        return null;
    }

}
