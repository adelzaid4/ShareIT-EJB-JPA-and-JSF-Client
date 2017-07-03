/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.dto;

import eg.iti.shareit.common.dto.GenericDto;
import eg.iti.shareit.model.entity.ItemEntity;
import eg.iti.shareit.model.entity.UserEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Adel Zaid
 */
public class NotificationDto implements Serializable, GenericDto {

    private BigDecimal id;
    private BigInteger pointsDeducted;
    private BigInteger days;
    private String meetingPoint;
    private ItemDto item;
    private UserDto fromUser;
    private UserDto toUser;
    private BigInteger seen;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getPointsDeducted() {
        return pointsDeducted;
    }

    public void setPointsDeducted(BigInteger pointsDeducted) {
        this.pointsDeducted = pointsDeducted;
    }

    public ItemDto getItem() {
        return item;
    }

    public void setItem(ItemDto item) {
        this.item = item;
    }

    public UserDto getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserDto fromUser) {
        this.fromUser = fromUser;
    }

    public UserDto getToUser() {
        return toUser;
    }

    public void setToUser(UserDto toUser) {
        this.toUser = toUser;
    }

    public BigInteger getDays() {
        return days;
    }

    public void setDays(BigInteger days) {
        this.days = days;
    }

    public String getMeetingPoint() {
        return meetingPoint;
    }

    public void setMeetingPoint(String meetingPoint) {
        this.meetingPoint = meetingPoint;
    }

    public BigInteger getSeen() {
        return seen;
    }

    public void setSeen(BigInteger seen) {
        this.seen = seen;
    }

}
