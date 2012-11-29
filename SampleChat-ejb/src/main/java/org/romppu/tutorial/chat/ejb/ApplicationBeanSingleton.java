/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.ejb;

import org.romppu.tutorial.chat.common.ChatMessage;
import org.romppu.tutorial.chat.common.SampleChatException;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.jms.*;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author roman
 */
@Singleton
@LocalBean
public class ApplicationBeanSingleton implements Serializable {

    private Logger logger = SampleChatLogger.getLogger(ApplicationBeanSingleton.class);
    
    private Map<String, Object> sessionMap = new Hashtable();

    @Resource(lookup="jms/IntranetChatTopicConnectionFactory")
    TopicConnectionFactory topicConnectionFactory;

    @Resource(lookup="jms/IntranetChatTopic")
    Topic topic;

    @Resource
    SessionContext context;

    private TopicConnection topicConnection;
    private TopicSession topicSession;
    private TopicPublisher topicPublisher;
    private SecurityManager securityManager;

    public Message createMessage() throws JMSException {
        return topicSession.createMessage();
    }

    public BytesMessage createBytesMessage() throws JMSException {
        return topicSession.createBytesMessage();
    }

    public void publish(Message message) throws JMSException, SampleChatException {
        if (!sessionMap.containsKey(message.getStringProperty(ChatMessage.NICK))) throw new SampleChatException("cannot_publish_from_anonymous");
        logger.info("Publish message from " + message.getStringProperty(ChatMessage.NICK));
        topicPublisher.publish(message);
    }

    public List getNickNameList() {
        return Collections.unmodifiableList(new Vector(sessionMap.keySet()));
    }

    public void registerSession(String nick) throws SampleChatException {
        if (sessionMap.containsKey(nick)) throw new SampleChatException("nick_name_already_exists", nick);
        sessionMap.put(nick, new Object());
        logger.info("register session for " + nick);
    }

    public void unregisterSession(String nick) throws SampleChatException {
        if (!sessionMap.containsKey(nick)) throw new SampleChatException("nick_name_doesnt_exist", nick);
        sessionMap.remove(nick);
    }

    @PostConstruct
    private void init() {
        try {
            IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
            securityManager = factory.getInstance();
            SecurityUtils.setSecurityManager(securityManager);
            topicConnection = topicConnectionFactory.createTopicConnection();
            topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            topicPublisher = topicSession.createPublisher(topic);
        } catch (Exception ex) {
            logger.error("Cannot initialize application", ex);
        }
    }

    public SecurityManager getSecurityManager() {
        return securityManager;
    }

}
