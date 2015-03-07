/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mmixco
 */
@Entity
@Table(name = "mov_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovDet.findAll", query = "SELECT m FROM MovDet m"),
    @NamedQuery(name = "MovDet.findByCodTipoDoc", query = "SELECT m FROM MovDet m WHERE m.codTipoDoc = :codTipoDoc"),
    @NamedQuery(name = "MovDet.findByCodMov", query = "SELECT m FROM MovDet m WHERE m.movDetPK.codMov = :codMov"),
    @NamedQuery(name = "MovDet.findByFecha", query = "SELECT m FROM MovDet m WHERE m.fecha = :fecha"),
    @NamedQuery(name = "MovDet.findByCorrelativo", query = "SELECT m FROM MovDet m WHERE m.movDetPK.correlativo = :correlativo"),
    @NamedQuery(name = "MovDet.findByCantidad", query = "SELECT m FROM MovDet m WHERE m.cantidad = :cantidad"),

    @NamedQuery(name = "MovDet.findByIva", query = "SELECT m FROM MovDet m WHERE m.iva = :iva"),
    @NamedQuery(name = "MovDet.findByTotal", query = "SELECT m FROM MovDet m WHERE m.total = :total")})
public class MovDet implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MovDetPK movDetPK;
    @Column(name = "cod_tipo_doc")
    private Integer codTipoDoc;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "precio")
    private Long precio;
    @Column(name = "iva")
    private Long iva;
    @Column(name = "total")
    private Long total;
    @JoinColumn(name = "cod_prod", referencedColumnName = "cod_prod")
    @ManyToOne
    private Producto codProd;

    public MovDet() {
    }

    public MovDet(MovDetPK movDetPK) {
        this.movDetPK = movDetPK;
    }

    public MovDet(int codMov, int correlativo) {
        this.movDetPK = new MovDetPK(codMov, correlativo);
    }

    public MovDetPK getMovDetPK() {
        return movDetPK;
    }

    public void setMovDetPK(MovDetPK movDetPK) {
        this.movDetPK = movDetPK;
    }

    public Integer getCodTipoDoc() {
        return codTipoDoc;
    }

    public void setCodTipoDoc(Integer codTipoDoc) {
        this.codTipoDoc = codTipoDoc;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
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

    public Producto getCodProd() {
        return codProd;
    }

    public void setCodProd(Producto codProd) {
        this.codProd = codProd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (movDetPK != null ? movDetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovDet)) {
            return false;
        }
        MovDet other = (MovDet) object;
        if ((this.movDetPK == null && other.movDetPK != null) || (this.movDetPK != null && !this.movDetPK.equals(other.movDetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.MovDet[ movDetPK=" + movDetPK + " ]";
    }
    
}
