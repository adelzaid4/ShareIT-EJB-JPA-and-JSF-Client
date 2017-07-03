/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.dao;

import eg.iti.shareit.common.Exception.DatabaseException;
import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.model.entity.ActivityEntity;
import eg.iti.shareit.model.entity.BorrowStateEntity;
import eg.iti.shareit.model.entity.ItemEntity;
import eg.iti.shareit.model.entity.UserEntity;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Adel Zaid
 */
@Stateless(mappedName = "BorrowStateImpl")
public class BorrowStateImpl extends GenericDaoImpl<BorrowStateEntity> implements BorrowStateDao {

    @EJB
    ActivityDao activityDao;
    @EJB
    ItemDao itemDao;

    public BorrowStateImpl() {
        super(BorrowStateEntity.class);
    }

    @Override
    public void handleBorrowingDueDate(UserEntity userEntity) throws DatabaseRollbackException {
        try {

            List<ActivityEntity> activityEntities = activityDao.getAvtivityOfUser(userEntity);
            for (ActivityEntity activityEntity : activityEntities) {
                if (!isActivityInserted(activityEntity)) {
                    if (activityEntity.getTimeTo().equals(new Date()) || activityEntity.getTimeTo().before(new Date())) {

                        BorrowStateEntity borrowStateEntity = new BorrowStateEntity();
                        borrowStateEntity.setActivity(activityEntity);
                        borrowStateEntity.setIsBack(BigInteger.valueOf(0));
                        save(borrowStateEntity);
                    }
                }
            }
        } catch (DatabaseException e) {
            throw new DatabaseRollbackException(e.getMessage());
        }
    }

    @Override
    public List<BorrowStateEntity> getBorrowStatus(UserEntity userEntity) throws DatabaseRollbackException {
        Query query = getEntityManager().createQuery("Select b From BorrowStateEntity b where b.activity.toUser=" + userEntity.getId() + " and  b.isBack =0");
        List<BorrowStateEntity> borrowStateEntities = query.getResultList();
        if (borrowStateEntities != null) {
            return borrowStateEntities;
        }
        return null;
    }

    public boolean isActivityInserted(ActivityEntity activityEntity) throws DatabaseRollbackException {
        Query query = getEntityManager().createQuery("Select b From BorrowStateEntity b where b.activity.id=" + activityEntity.getId());
        List<BorrowStateEntity> borrowStateEntities = query.getResultList();
        if (borrowStateEntities.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void updateBorrowStatus(BorrowStateEntity borrowStateEntity) throws DatabaseRollbackException {
        //borrowStateEntity.setIsBack(BigInteger.valueOf(1));

        //borrowStateEntity.getActivity().getItem();
        update(borrowStateEntity);
        ActivityEntity activityEntity = borrowStateEntity.getActivity();
        activityEntity.setBorrowStateEntityList(new ArrayList<BorrowStateEntity>());
        activityEntity.getBorrowStateEntityList().add(borrowStateEntity);
        getEntityManager().merge(activityEntity);
        //("activity is "+activityEntity);
        //save(borrowStateEntity);

        ItemEntity itemEntity = borrowStateEntity.getActivity().getItem();
        itemEntity.setIsAvailable((short) 1);
        itemDao.updateItem(itemEntity);

    }

    @Override
    public int getItemStatusNumber(BigDecimal userID) throws DatabaseRollbackException {
        Query query = getEntityManager().createQuery("Select count(b.isBack) From BorrowStateEntity b where b.activity.toUser=" + userID.intValue() + " and  b.isBack =0");
        long singleResult = (long) query.getSingleResult();
        return (int) singleResult;
    }
}
