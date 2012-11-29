/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.ejb;

import org.romppu.tutorial.chat.common.ChatMessage;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author roman
 */
@MessageDriven(mappedName = "jms/IntranetChatTopic", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "IntranetChatMessageLogger"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "IntranetChatMessageLogger")
})
public class IntranetChatMessageLogger implements MessageListener {
    
    private static final Logger logger = Logger.getLogger(IntranetChatMessageLogger.class.getName());

    @EJB
    MessagePersistent messagePersistent;
            
    public IntranetChatMessageLogger() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            if (message.getStringProperty(ChatMessage.TYPE).equals(ChatMessage.Type.MESSAGE.toString())) {
                BytesMessage bytesMessage = (BytesMessage)message;
                byte[] messageBytes = new byte[(int)bytesMessage.getBodyLength()];
                bytesMessage.readBytes(messageBytes);
                messagePersistent.createMessage(
                        message.getStringProperty(ChatMessage.NICK), 
                        message.getStringProperty(ChatMessage.MESSAGE),
                        new Date(message.getJMSTimestamp()),
                        messageBytes);
            }
        } catch (JMSException ex) {
            logger.log(Level.SEVERE, "Cannot write message to db", ex);
        }
    }
}
