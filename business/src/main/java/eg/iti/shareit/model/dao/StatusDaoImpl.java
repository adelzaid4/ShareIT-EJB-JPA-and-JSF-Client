/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.dao;

import eg.iti.shareit.model.entity.StatusEntity;
import javax.ejb.Stateless;

@Stateless(mappedName = "StatusDaoImpl")
public class StatusDaoImpl extends GenericDaoImpl<StatusEntity>implements StatusDao {

    public StatusDaoImpl() {
        super(StatusEntity.class);
    }

}
