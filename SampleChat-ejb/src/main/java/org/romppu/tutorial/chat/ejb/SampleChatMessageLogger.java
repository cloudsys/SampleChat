/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.ejb;

import org.apache.log4j.Logger;
import org.romppu.tutorial.chat.common.ChatMessage;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Date;

/**
 *
 * @author roman
 */
@MessageDriven(mappedName = "jms/IntranetChatTopic", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "SampleChatMessageLogger"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "SampleChatMessageLogger")
})
public class SampleChatMessageLogger implements MessageListener {
    
    private static final Logger logger = SampleChatLogger.getLogger(SampleChatMessageLogger.class);

    @EJB
    MessagePersistence messagePersistence;
            
    public SampleChatMessageLogger() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            if (message.getStringProperty(ChatMessage.TYPE).equals(ChatMessage.Type.MESSAGE.toString())) {
                BytesMessage bytesMessage = (BytesMessage)message;
                byte[] messageBytes = new byte[(int)bytesMessage.getBodyLength()];
                bytesMessage.readBytes(messageBytes);
                messagePersistence.createMessage(
                        message.getStringProperty(ChatMessage.NICK),
                        message.getStringProperty(ChatMessage.MESSAGE),
                        new Date(message.getJMSTimestamp()),
                        messageBytes);
            }
        } catch (JMSException ex) {
            logger.error("Cannot save message in database", ex);
        }
    }
}
