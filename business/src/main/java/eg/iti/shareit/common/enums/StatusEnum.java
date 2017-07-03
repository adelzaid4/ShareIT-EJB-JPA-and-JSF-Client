/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.common.enums;

import eg.iti.shareit.model.dto.StatusDto;
import eg.iti.shareit.model.entity.StatusEntity;
import java.math.BigDecimal;

/**
 *
 * @author Adel Zaid
 */
public enum StatusEnum {
    PENDING(new StatusEntity(new BigDecimal(1), "Pending")), ACCEPTED(new StatusEntity(new BigDecimal(2), "Accepted")), DECLINED(new StatusEntity(new BigDecimal(3), "Declined"));

    private StatusEnum(StatusEntity status) {
        this.status = status;

    }

    private StatusEntity status;

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

}
