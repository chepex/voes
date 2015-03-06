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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mmixco
 */
@Entity
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByCodProd", query = "SELECT p FROM Producto p WHERE p.codProd = :codProd"),
    @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Producto.findByCosto", query = "SELECT p FROM Producto p WHERE p.costo = :costo"),
    @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio"),
    @NamedQuery(name = "Producto.findByExistencia", query = "SELECT p FROM Producto p WHERE p.existencia = :existencia"),
    @NamedQuery(name = "Producto.findByMaxExistencia", query = "SELECT p FROM Producto p WHERE p.maxExistencia = :maxExistencia"),
    @NamedQuery(name = "Producto.findByMinExistencia", query = "SELECT p FROM Producto p WHERE p.minExistencia = :minExistencia")})
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_prod")
    private Integer codProd;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "costo")
    private Integer costo;
    @Column(name = "precio")
    private Integer precio;
    @Column(name = "existencia")
    private Integer existencia;
    @Column(name = "max_existencia")
    private Integer maxExistencia;
    @Column(name = "min_existencia")
    private Integer minExistencia;
    @OneToMany(mappedBy = "codProd")
    private List<MovDet> movDetList;

    public Producto() {
    }

    public Producto(Integer codProd) {
        this.codProd = codProd;
    }

    public Integer getCodProd() {
        return codProd;
    }

    public void setCodProd(Integer codProd) {
        this.codProd = codProd;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getExistencia() {
        return existencia;
    }

    public void setExistencia(Integer existencia) {
        this.existencia = existencia;
    }

    public Integer getMaxExistencia() {
        return maxExistencia;
    }

    public void setMaxExistencia(Integer maxExistencia) {
        this.maxExistencia = maxExistencia;
    }

    public Integer getMinExistencia() {
        return minExistencia;
    }

    public void setMinExistencia(Integer minExistencia) {
        this.minExistencia = minExistencia;
    }

    @XmlTransient
    public List<MovDet> getMovDetList() {
        return movDetList;
    }

    public void setMovDetList(List<MovDet> movDetList) {
        this.movDetList = movDetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codProd != null ? codProd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.codProd == null && other.codProd != null) || (this.codProd != null && !this.codProd.equals(other.codProd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Producto[ codProd=" + codProd + " ]";
    }
    
}
