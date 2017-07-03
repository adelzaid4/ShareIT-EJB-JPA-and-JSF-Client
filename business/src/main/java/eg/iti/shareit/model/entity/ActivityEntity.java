/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.entity;

import eg.iti.shareit.common.Exception.DatabaseRollbackException;
import eg.iti.shareit.common.dao.GenericDao;
import eg.iti.shareit.common.entity.GenericEntity;
import eg.iti.shareit.model.dao.UserDao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Adel Zaid
 */
@Entity
@Table(name = "T_ACTIVITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActivityEntity.findAll", query = "SELECT t FROM ActivityEntity t"),
    @NamedQuery(name = "ActivityEntity.findById", query = "SELECT t FROM ActivityEntity t WHERE t.id = :id"),
    @NamedQuery(name = "ActivityEntity.findByMeetingPoint", query = "SELECT t FROM ActivityEntity t WHERE t.meetingPoint = :meetingPoint"),
    @NamedQuery(name = "ActivityEntity.findByStatus", query = "SELECT t FROM ActivityEntity t WHERE t.status = :status"),
    @NamedQuery(name = "ActivityEntity.findByTimeFrom", query = "SELECT t FROM ActivityEntity t WHERE t.timeFrom = :timeFrom"),
    @NamedQuery(name = "ActivityEntity.findByTimeTo", query = "SELECT t FROM ActivityEntity t WHERE t.timeTo = :timeTo"),
    @NamedQuery(name = "ActivityEntity.findByActivityDeleted", query = "SELECT t FROM ActivityEntity t WHERE t.activityDeleted = :activityDeleted")})
public class ActivityEntity implements Serializable,
        GenericEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activity")
    private List<BorrowStateEntity> borrowStateEntityList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_ACTIVITY_SEQ")
    @SequenceGenerator(name = "T_ACTIVITY_SEQ", sequenceName = "T_ACTIVITY_SEQ", allocationSize = 100, initialValue = 1)
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "MEETING_POINT")
    private String meetingPoint;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeFrom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME_TO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeTo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTIVITY_DELETED")
    private short activityDeleted;
    @JoinColumn(name = "ITEM", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ItemEntity item;
    @JoinColumn(name = "TO_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserEntity toUser;
    @JoinColumn(name = "FROM_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserEntity fromUser;
    @JoinColumn(name = "STATUS", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StatusEntity status;

    public ActivityEntity() {
    }

    public ActivityEntity(BigDecimal id) {
        this.id = id;
    }

    public ActivityEntity(BigDecimal id, String meetingPoint, StatusEntity status, Date timeFrom, Date timeTo, short activityDeleted) {
        this.id = id;
        this.meetingPoint = meetingPoint;
        this.status = status;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.activityDeleted = activityDeleted;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getMeetingPoint() {
        return meetingPoint;
    }

    public void setMeetingPoint(String meetingPoint) {
        this.meetingPoint = meetingPoint;
    }

    public Date getTimeFrom() {
        return timeFrom;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public void setTimeFrom(Date timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Date getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Date timeTo) {
        this.timeTo = timeTo;
    }

    public short getActivityDeleted() {
        return activityDeleted;
    }

    public void setActivityDeleted(short activityDeleted) {
        this.activityDeleted = activityDeleted;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public UserEntity getToUser() {
        return toUser;
    }

    public void setToUser(UserEntity toUser) {
        this.toUser = toUser;
    }

    public UserEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserEntity fromUser) {
        this.fromUser = fromUser;
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
        if (!(object instanceof ActivityEntity)) {
            return false;
        }
        ActivityEntity other = (ActivityEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eg.iti.shareit.model.entity.ActivityEntity[ id=" + id + " ]";
    }

    @XmlTransient
    public List<BorrowStateEntity> getBorrowStateEntityList() {
        return borrowStateEntityList;
    }

    public void setBorrowStateEntityList(List<BorrowStateEntity> borrowStateEntityList) {
        this.borrowStateEntityList = borrowStateEntityList;
    }

}
