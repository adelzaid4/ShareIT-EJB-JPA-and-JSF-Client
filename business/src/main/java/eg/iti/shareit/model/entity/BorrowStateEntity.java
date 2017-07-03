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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adel Zaid
 */
@Entity
@Table(name = "T_BORROW_STATE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BorrowStateEntity.findAll", query = "SELECT b FROM BorrowStateEntity b"),
    @NamedQuery(name = "BorrowStateEntity.findById", query = "SELECT b FROM BorrowStateEntity b WHERE b.id = :id"),
    @NamedQuery(name = "BorrowStateEntity.findByIsBack", query = "SELECT b FROM BorrowStateEntity b WHERE b.isBack = :isBack")})
public class BorrowStateEntity implements Serializable, GenericEntity {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_BORROW_STATE_SEQ")
    @SequenceGenerator(name = "T_BORROW_STATE_SEQ", sequenceName = "T_BORROW_STATE_SEQ", allocationSize = 1, initialValue = 1)
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_BACK")
    private BigInteger isBack;
    @JoinColumn(name = "ACTIVITY", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ActivityEntity activity;

    public BorrowStateEntity() {
    }

    public BorrowStateEntity(BigDecimal id) {
        this.id = id;
    }

    public BorrowStateEntity(BigDecimal id, BigInteger isBack) {
        this.id = id;
        this.isBack = isBack;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getIsBack() {
        return isBack;
    }

    public void setIsBack(BigInteger isBack) {
        this.isBack = isBack;
    }

    public ActivityEntity getActivity() {
        return activity;
    }

    public void setActivity(ActivityEntity activity) {
        this.activity = activity;
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
        if (!(object instanceof BorrowStateEntity)) {
            return false;
        }
        BorrowStateEntity other = (BorrowStateEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eg.iti.shareit.model.entity.BorrowStateEntity[ id=" + id + " ]";
    }

}