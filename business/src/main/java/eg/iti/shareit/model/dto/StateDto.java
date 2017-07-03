/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.dto;

import eg.iti.shareit.common.dto.GenericDto;
import java.math.BigDecimal;

/**
 *
 * @author Yousef
 */
public class StateDto implements java.io.Serializable, GenericDto {

    private BigDecimal id;
    private String state;

    public StateDto() {
    }

    public StateDto(BigDecimal id, String state) {
        this.id = id;
        this.state = state;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "StateDto{" + "id=" + id + ", state=" + state + '}';
    }
}
