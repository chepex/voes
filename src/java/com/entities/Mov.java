/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mmixco
 */
@Entity
@Table(name = "mov")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mov.findAll", query = "SELECT m FROM Mov m"),
    @NamedQuery(name = "Mov.findByCodTipoDoc", query = "SELECT m FROM Mov m WHERE m.codTipoDoc = :codTipoDoc"),
    @NamedQuery(name = "Mov.findByCodMov", query = "SELECT m FROM Mov m WHERE m.codMov = :codMov"),
    @NamedQuery(name = "Mov.findByFecha", query = "SELECT m FROM Mov m WHERE m.fecha = :fecha"),
    @NamedQuery(name = "Mov.findBySubtotal", query = "SELECT m FROM Mov m WHERE m.subtotal = :subtotal"),
    @NamedQuery(name = "Mov.findByIva", query = "SELECT m FROM Mov m WHERE m.iva = :iva"),
    @NamedQuery(name = "Mov.findByTotal", query = "SELECT m FROM Mov m WHERE m.total = :total")})
public class Mov implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "cod_tipo_doc")
    private Integer codTipoDoc;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_mov")
    private Integer codMov;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "subtotal")
    private Long subtotal;
    @Column(name = "iva")
    private Long iva;
    @Column(name = "total")
    private Long total;

    public Mov() {
    }

    public Mov(Integer codMov) {
        this.codMov = codMov;
    }

    public Integer getCodTipoDoc() {
        return codTipoDoc;
    }

    public void setCodTipoDoc(Integer codTipoDoc) {
        this.codTipoDoc = codTipoDoc;
    }

    public Integer getCodMov() {
        return codMov;
    }

    public void setCodMov(Integer codMov) {
        this.codMov = codMov;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
    }

    public Long getIva() {
        return iva;
    }

    public void setIva(Long iva) {
        this.iva = iva;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codMov != null ? codMov.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mov)) {
            return false;
        }
        Mov other = (Mov) object;
        if ((this.codMov == null && other.codMov != null) || (this.codMov != null && !this.codMov.equals(other.codMov))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Mov[ codMov=" + codMov + " ]";
    }
    
}
