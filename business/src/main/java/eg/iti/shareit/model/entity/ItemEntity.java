/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.entity;

import eg.iti.shareit.common.entity.GenericEntity;
import eg.iti.shareit.model.entity.CategoryEntity;
import eg.iti.shareit.model.entity.UserEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "T_ITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemEntity.findAll", query = "SELECT i FROM ItemEntity i"),
    @NamedQuery(name = "ItemEntity.findById", query = "SELECT i FROM ItemEntity i WHERE i.id = :id"),
    @NamedQuery(name = "ItemEntity.findByName", query = "SELECT i FROM ItemEntity i WHERE i.name = :name"),
    @NamedQuery(name = "ItemEntity.findByDescription", query = "SELECT i FROM ItemEntity i WHERE i.description = :description"),
    @NamedQuery(name = "ItemEntity.findByIsAvailable", query = "SELECT i FROM ItemEntity i WHERE i.isAvailable = :isAvailable"),
    @NamedQuery(name = "ItemEntity.findByPublishDate", query = "SELECT i FROM ItemEntity i WHERE i.publishDate = :publishDate"),
    @NamedQuery(name = "ItemEntity.findByPoints", query = "SELECT i FROM ItemEntity i WHERE i.points = :points"),
    @NamedQuery(name = "ItemEntity.findByImageUrl", query = "SELECT i FROM ItemEntity i WHERE i.imageUrl = :imageUrl"),
    @NamedQuery(name = "ItemEntity.findByTags", query = "SELECT i FROM ItemEntity i WHERE i.tags = :tags")})
public class ItemEntity implements Serializable, GenericEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    private List<ActivityEntity> activityEntityList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    private List<NotificationEntity> notificationEntityList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_ITEM_SEQ")
    @SequenceGenerator(name = "T_ITEM_SEQ", sequenceName = "T_ITEM_SEQ", allocationSize = 1, initialValue = 1)
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "NAME")
    private String name;
    @Size(max = 1000)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_AVAILABLE")
    private short isAvailable;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PUBLISH_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POINTS")
    private BigInteger points;
    @Size(min = 1, max = 500)
    @Column(name = "IMAGE_URL")
    private String imageUrl;
    @Basic(optional = false)
    @NotNull
    @Size(max = 200)
    @Column(name = "TAGS")
    private String tags;
    @JoinColumn(name = "CATEGORY", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CategoryEntity category;
    @JoinColumn(name = "USER_FROM", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserEntity userFrom;

    public ItemEntity() {
    }

    public ItemEntity(BigDecimal id) {
        this.id = id;
    }

    public ItemEntity(BigDecimal id, String name, short isAvailable, Date publishDate, BigInteger points, String imageUrl, String tags) {
        this.id = id;
        this.name = name;
        this.isAvailable = isAvailable;
        this.publishDate = publishDate;
        this.points = points;
        this.imageUrl = imageUrl;
        this.tags = tags;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(short isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public BigInteger getPoints() {
        return points;
    }

    public void setPoints(BigInteger points) {
        this.points = points;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public UserEntity getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(UserEntity userFrom) {
        this.userFrom = userFrom;
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
        if (!(object instanceof ItemEntity)) {
            return false;
        }
        ItemEntity other = (ItemEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eg.iti.shareit.common.enums.ItemEntity[ id=" + id + " ]";
    }

    @XmlTransient
    public List<NotificationEntity> getNotificationEntityList() {
        return notificationEntityList;
    }

    public void setNotificationEntityList(List<NotificationEntity> notificationEntityList) {
        this.notificationEntityList = notificationEntityList;
    }

    @XmlTransient
    public List<ActivityEntity> getActivityEntityList() {
        return activityEntityList;
    }

    public void setActivityEntityList(List<ActivityEntity> activityEntityList) {
        this.activityEntityList = activityEntityList;
    }

}
