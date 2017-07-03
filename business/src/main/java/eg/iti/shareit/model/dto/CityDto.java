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
public class CityDto implements java.io.Serializable, GenericDto {

    private BigDecimal id;
    private String city;

    public CityDto() {
    }

    public CityDto(BigDecimal id, String city) {
        this.id = id;
        this.city = city;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "CityDto{" + "id=" + id + ", city=" + city + '}';
    }

}
