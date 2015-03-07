/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mmixco
 */
@Embeddable
public class MovPagoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_tipo_pago")
    private int codTipoPago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_mov")
    private int codMov;

    public MovPagoPK() {
    }

    public MovPagoPK(int codTipoPago, int codMov) {
        this.codTipoPago = codTipoPago;
        this.codMov = codMov;
    }

    public int getCodTipoPago() {
        return codTipoPago;
    }

    public void setCodTipoPago(int codTipoPago) {
        this.codTipoPago = codTipoPago;
    }

    public int getCodMov() {
        return codMov;
    }

    public void setCodMov(int codMov) {
        this.codMov = codMov;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codTipoPago;
        hash += (int) codMov;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovPagoPK)) {
            return false;
        }
        MovPagoPK other = (MovPagoPK) object;
        if (this.codTipoPago != other.codTipoPago) {
            return false;
        }
        if (this.codMov != other.codMov) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.MovPagoPK[ codTipoPago=" + codTipoPago + ", codMov=" + codMov + " ]";
    }
    
}
