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
public class CountryDto implements java.io.Serializable, GenericDto {

    private BigDecimal id;
    private String country;

    public CountryDto() {
    }

    public CountryDto(BigDecimal id, String country) {
        this.id = id;
        this.country = country;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "CountryDto{" + "id=" + id + ", country=" + country + '}';
    }

}
