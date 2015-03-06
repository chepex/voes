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
public class MovDetPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_mov")
    private int codMov;
    @Basic(optional = false)
    @NotNull
    @Column(name = "correlativo")
    private int correlativo;

    public MovDetPK() {
    }

    public MovDetPK(int codMov, int correlativo) {
        this.codMov = codMov;
        this.correlativo = correlativo;
    }

    public int getCodMov() {
        return codMov;
    }

    public void setCodMov(int codMov) {
        this.codMov = codMov;
    }

    public int getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(int correlativo) {
        this.correlativo = correlativo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codMov;
        hash += (int) correlativo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovDetPK)) {
            return false;
        }
        MovDetPK other = (MovDetPK) object;
        if (this.codMov != other.codMov) {
            return false;
        }
        if (this.correlativo != other.correlativo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.MovDetPK[ codMov=" + codMov + ", correlativo=" + correlativo + " ]";
    }
    
}
