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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Adel Zaid
 */
@Entity
@Table(name = "T_USER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserEntity.findAll", query = "SELECT u FROM UserEntity u"),
    @NamedQuery(name = "UserEntity.findById", query = "SELECT u FROM UserEntity u WHERE u.id = :id"),
    @NamedQuery(name = "UserEntity.findByUsername", query = "SELECT u FROM UserEntity u WHERE u.username = :username"),
    @NamedQuery(name = "UserEntity.findByEmail", query = "SELECT u FROM UserEntity u WHERE u.email = :email"),
    @NamedQuery(name = "UserEntity.findByPassword", query = "SELECT u FROM UserEntity u WHERE u.password = :password"),
    @NamedQuery(name = "UserEntity.findByImageUrl", query = "SELECT u FROM UserEntity u WHERE u.imageUrl = :imageUrl"),
    @NamedQuery(name = "UserEntity.findByPoints", query = "SELECT u FROM UserEntity u WHERE u.points = :points")})
public class UserEntity implements Serializable, GenericEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fromUser", fetch = FetchType.LAZY)
    private List<ActivityEntity> activityEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "toUser", fetch = FetchType.LAZY)
    private List<ActivityEntity> activityEntityList1;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fromUser", fetch = FetchType.LAZY)
    private List<NotificationEntity> notificationFromUserList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "toUser", fetch = FetchType.LAZY)
    private List<NotificationEntity> notificationToUserList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userFrom", fetch = FetchType.LAZY)
    private List<ItemEntity> items;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_USER_SEQ")
    @SequenceGenerator(name = "T_USER_SEQ", sequenceName = "T_USER_SEQ", allocationSize = 1, initialValue = 1)
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USERNAME")
    private String username;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 500)
    @Column(name = "IMAGE_URL")
    private String imageUrl;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POINTS")
    private BigInteger points;
    @JoinColumn(name = "ADDRESS", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private AddressEntity address;
    @JoinColumn(name = "GENDER", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GenderEntity gender;

    public UserEntity() {
    }

    public UserEntity(BigDecimal id) {
        this.id = id;
    }

    public UserEntity(BigDecimal id, String username, String email, String password, BigInteger points) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.points = points;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigInteger getPoints() {
        return points;
    }

    public void setPoints(BigInteger points) {
        this.points = points;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public GenderEntity getGender() {
        return gender;
    }

    public void setGender(GenderEntity gender) {
        this.gender = gender;
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
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eg.iti.shareit.model.entity.UserEntity[ id=" + id + " ]";
    }

    @XmlTransient
    public List<NotificationEntity> getNotificationFromUserList() {
        return notificationFromUserList;
    }

    public void setNotificationFromUserList(List<NotificationEntity> notificationFromUserList) {
        this.notificationFromUserList = notificationFromUserList;
    }

    @XmlTransient
    public List<NotificationEntity> getNotificationToUserList() {
        return notificationToUserList;
    }

    public void setNotificationToUserList(List<NotificationEntity> notificationToUserList) {
        this.notificationToUserList = notificationToUserList;
    }

    @XmlTransient
    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }

    @XmlTransient
    public List<ActivityEntity> getActivityEntityList() {
        return activityEntityList;
    }

    public void setActivityEntityList(List<ActivityEntity> activityEntityList) {
        this.activityEntityList = activityEntityList;
    }

    @XmlTransient
    public List<ActivityEntity> getActivityEntityList1() {
        return activityEntityList1;
    }

    public void setActivityEntityList1(List<ActivityEntity> activityEntityList1) {
        this.activityEntityList1 = activityEntityList1;
    }

}
