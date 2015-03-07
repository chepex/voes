/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mmixco
 */
@Stateless
public class MovPagoFacade extends AbstractFacade<MovPago> {
    @PersistenceContext(unitName = "voesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MovPagoFacade() {
        super(MovPago.class);
    }
    
}
