/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.ejb;

import org.romppu.tutorial.chat.entity.TSurro;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author roman
 */
@Stateless
@LocalBean
public class SurroPersistence {
    
    @PersistenceContext(unitName="ICDB")
    EntityManager em;

    @Resource
    SessionContext sessionContext;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int getSurrogate(String tableName, String uui) {
        int value = -1;
        TSurro surro = em.find(TSurro.class, tableName);
        value = surro.getSurroValueInt() + 1;
        surro.setSurroValueInt(value);
        surro.setSurroUts(PersistenceUtil.getTimestamp());
        surro.setSurroUui(uui);
        em.merge(surro);
        return value;
    }

}
