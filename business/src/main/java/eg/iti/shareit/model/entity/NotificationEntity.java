/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.entity;

import eg.iti.shareit.common.entity.GenericEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adel Zaid
 */
@Entity
@Table(name = "T_NOTIFICATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificationEntity.findAll", query = "SELECT n FROM NotificationEntity n"),
    @NamedQuery(name = "NotificationEntity.findById", query = "SELECT n FROM NotificationEntity n WHERE n.id = :id"),
    @NamedQuery(name = "NotificationEntity.findByPointsDeducted", query = "SELECT n FROM NotificationEntity n WHERE n.pointsDeducted = :pointsDeducted"),
    @NamedQuery(name = "NotificationEntity.findByMeetingPoint", query = "SELECT n FROM NotificationEntity n WHERE n.meetingPoint = :meetingPoint"),
    @NamedQuery(name = "NotificationEntity.findByDays", query = "SELECT n FROM NotificationEntity n WHERE n.days = :days"),
    @NamedQuery(name = "NotificationEntity.findBySeen", query = "SELECT n FROM NotificationEntity n WHERE n.seen = :seen")})
public class NotificationEntity implements Serializable, GenericEntity {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_NOTIFICATION_SEQ")
    @SequenceGenerator(name = "T_NOTIFICATION_SEQ", sequenceName = "T_NOTIFICATION_SEQ", allocationSize = 50, initialValue = 1)
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POINTS_DEDUCTED")
    private BigInteger pointsDeducted;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MEETING_POINT")
    private String meetingPoint;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DAYS")
    private BigInteger days;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SEEN")
    private BigInteger seen;
    @JoinColumn(name = "ITEM", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ItemEntity item;
    @JoinColumn(name = "FROM_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserEntity fromUser;
    @JoinColumn(name = "TO_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserEntity toUser;

    public NotificationEntity() {
    }

    public NotificationEntity(BigDecimal id) {
        this.id = id;
    }

    public NotificationEntity(BigDecimal id, BigInteger pointsDeducted, String meetingPoint, BigInteger days, BigInteger seen) {
        this.id = id;
        this.pointsDeducted = pointsDeducted;
        this.meetingPoint = meetingPoint;
        this.days = days;
        this.seen = seen;
    }

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

    public String getMeetingPoint() {
        return meetingPoint;
    }

    public void setMeetingPoint(String meetingPoint) {
        this.meetingPoint = meetingPoint;
    }

    public BigInteger getDays() {
        return days;
    }

    public void setDays(BigInteger days) {
        this.days = days;
    }

    public BigInteger getSeen() {
        return seen;
    }

    public void setSeen(BigInteger seen) {
        this.seen = seen;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public UserEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserEntity fromUser) {
        this.fromUser = fromUser;
    }

    public UserEntity getToUser() {
        return toUser;
    }

    public void setToUser(UserEntity toUser) {
        this.toUser = toUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificationEntity)) {
            return false;
        }
        NotificationEntity other = (NotificationEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eg.iti.shareit.model.entity.NotificationEntity[ id=" + id + " ]";
    }

}
