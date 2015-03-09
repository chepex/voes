/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mmixco
 */
@Entity
@Table(name = "tipo_pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPago.findAll", query = "SELECT t FROM TipoPago t"),
    @NamedQuery(name = "TipoPago.findByCodTipoPago", query = "SELECT t FROM TipoPago t WHERE t.codTipoPago = :codTipoPago"),
    @NamedQuery(name = "TipoPago.findByNombre", query = "SELECT t FROM TipoPago t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoPago.findByEstado", query = "SELECT t FROM TipoPago t WHERE t.estado = :estado")})
public class TipoPago implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_tipo_pago")
    private int codTipoPago;
    @Size(max = 250)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @Size(max = 60)
    @Column(name = "img")
    private String img;    
    @OneToMany(mappedBy = "tipoPago")
    private List<MovPago> tipoPagoList;    
    

    public TipoPago() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    
    
    public TipoPago(Integer codTipoPago) {
        this.codTipoPago = codTipoPago;
    }

    public Integer getCodTipoPago() {
        return codTipoPago;
    }

    public void setCodTipoPago(Integer codTipoPago) {
        this.codTipoPago = codTipoPago;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    

    public List<MovPago> getTipoPagoList() {
        return tipoPagoList;
    }

    public void setTipoPagoList(List<MovPago> tipoPagoList) {
        this.tipoPagoList = tipoPagoList;
    }


    
    

    @Override
    public int hashCode() {
         int hash = 0;
        hash += (int) codTipoPago;
        
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPago)) {
            return false;
        }
      
        TipoPago other = (TipoPago) object;
        if (this.codTipoPago != other.codTipoPago) {
            return false;
        }        
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.TipoPago[ codTipoPago=" + codTipoPago + " ]";
    }
    
}
