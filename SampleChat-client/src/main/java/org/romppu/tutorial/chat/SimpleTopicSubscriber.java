/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat;

import org.romppu.tutorial.chat.common.ChatMessage;

import javax.imageio.ImageIO;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleTopicSubscriber {
    
    private static final Logger logger = Logger.getLogger(SampleChatApplication.class.getName());
 
    public interface TopicListener {
        public void onMessage(String nickName, String text, Image img);
        public void onCollaboratorArrived(String nickName);
        public void onCollaboratorLieved(String nickName);
    }
    
    private final TopicConnectionFactory topicConnectionFactory;
    private final TopicConnection topicConnection;
    private final TopicSession topicSession;
    private final Topic topic;
    private final TopicSubscriber topicSubscriber;

    public SimpleTopicSubscriber(String topicName, String connectionFactory, TopicListener listener) throws NamingException, JMSException, IOException {
        InitialContext context = new InitialContext();
        topicConnectionFactory = (TopicConnectionFactory) context.lookup(connectionFactory);
        topic = (Topic) context.lookup(topicName);
        topicConnection = topicConnectionFactory.createTopicConnection();
        topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        topicSubscriber = topicSession.createSubscriber(topic);
        topicSubscriber.setMessageListener(new InternalMessageListener(listener));
        topicConnection.start();
    }
    
    private class InternalMessageListener implements MessageListener {
        private final TopicListener topicListener;        
        
        private InternalMessageListener(TopicListener listener) {
            topicListener = listener;
        }
        
        @Override
        public void onMessage(Message message) {
            try {
                ChatMessage.Type type = ChatMessage.Type.valueOf(message.getStringProperty("type"));
                String nick = message.getStringProperty(ChatMessage.NICK);
                switch (type) {
                    case MESSAGE:
                        BytesMessage bytesMessage = (BytesMessage)message;
                        String text = message.getStringProperty(ChatMessage.MESSAGE);
                        Image image = null;
                        if (bytesMessage.getBodyLength() > 0) {
                            byte[] data = new byte[(int)bytesMessage.getBodyLength()];
                            bytesMessage.readBytes(data, data.length);
                            image = ImageIO.read(new ByteArrayInputStream(data));
                        }
                        topicListener.onMessage(nick, text, image);
                        break;
                    case COLLABORATOR_ARRIVE:
                        topicListener.onCollaboratorArrived(nick);
                        break;
                    case COLLABORATOR_LIEVE:
                        topicListener.onCollaboratorLieved(nick);
                        break;
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
}
