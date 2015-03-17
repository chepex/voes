/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mmixco
 */
@Entity
@Table(name = "mov_pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovPago.findAll", query = "SELECT m FROM MovPago m"),
    @NamedQuery(name = "MovPago.findByCodTipoPago", query = "SELECT m FROM MovPago m WHERE m.movPagoPK.codTipoPago = :codTipoPago"),
    @NamedQuery(name = "MovPago.findByCodMov", query = "SELECT m FROM MovPago m WHERE m.movPagoPK.codMov = :codMov"),
    @NamedQuery(name = "MovPago.findByValor", query = "SELECT m FROM MovPago m WHERE m.valor = :valor"),
    @NamedQuery(name = "MovPago.findByCorrelativo", query = "SELECT m FROM MovPago m WHERE m.correlativo = :correlativo")})
public class MovPago implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MovPagoPK movPagoPK;
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "correlativo")
    private Integer correlativo;
    @Column(name = "referencia")
    private String referencia;
    @JoinColumn(name = "cod_tipo_pago", referencedColumnName = "cod_tipo_pago" ,insertable=false, updatable=false)
    @ManyToOne
    private TipoPago tipoPago;    
    

    public MovPago() {
    }

    public MovPago(MovPagoPK movPagoPK) {
        this.movPagoPK = movPagoPK;
    }

    public MovPago(int codTipoPago, int codMov) {
        this.movPagoPK = new MovPagoPK(codTipoPago, codMov);
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    
    public MovPagoPK getMovPagoPK() {
        return movPagoPK;
    }

    public void setMovPagoPK(MovPagoPK movPagoPK) {
        this.movPagoPK = movPagoPK;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    

    public Integer getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(Integer correlativo) {
        this.correlativo = correlativo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (movPagoPK != null ? movPagoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovPago)) {
            return false;
        }
        MovPago other = (MovPago) object;
        if ((this.movPagoPK == null && other.movPagoPK != null) || (this.movPagoPK != null && !this.movPagoPK.equals(other.movPagoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.MovPago[ movPagoPK=" + movPagoPK + " ]";
    }
    
}
