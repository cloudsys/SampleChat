/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.ejb;

import org.romppu.tutorial.chat.entity.TMessage;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author roman
 */
@Stateless
@LocalBean
public class MessagePersistent implements Serializable {
    
    @PersistenceContext(unitName="ICDB")
    EntityManager em;

    @EJB
    SurroPersistence surroPersistence;
           
    @Resource
    SessionContext sessionContext;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)    
    public void createMessage(String sender, String text, Date sendDate, byte[] imageBytes) {
        TMessage message = new TMessage();
        message.setMsgId(surroPersistence.getSurrogate("MESSAGE", sender));
        message.setMsgSendDte(PersistenceUtil.getTimestamp(sendDate));
        message.setMsgImgBlo(imageBytes);
        message.setMsgSenderNme(sender);
        message.setMsgMessageChr(text);
        em.persist(message);
    }
}
