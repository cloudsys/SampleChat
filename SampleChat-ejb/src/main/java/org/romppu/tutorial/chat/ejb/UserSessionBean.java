/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.ejb;

import org.romppu.tutorial.chat.common.ChatMessage;
import org.romppu.tutorial.chat.common.ImageData;
import org.romppu.tutorial.chat.common.SampleChatException;
import org.romppu.tutorial.chat.ejb.annotation.PermissionsRequired;
import org.romppu.tutorial.chat.ejb.annotation.RolesRequired;
import org.romppu.tutorial.chat.ejb.annotation.Secured;
import org.romppu.tutorial.chat.ejb.remote.UserSessionBeanRemote;
import org.romppu.tutorial.chat.ejb.security.SecuredSubjectOwner;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateful;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.List;

/**
 *
 * @author roman
 */
@Stateful
@Secured
public class UserSessionBean implements UserSessionBeanRemote, SecuredSubjectOwner {

    private static final Logger logger = ICLogger.getLogger(UserSessionBean.class);

    @EJB
    ApplicationBeanSingleton applicationBeanSingleton;

    private Subject subject;

    private String getUserName() {
        return getSubject().getPrincipal().toString();
    }

    @Override
    @RolesRequired("COLLABORATOR")
    public String getLoginName() throws SampleChatException {
        return getUserName();
    }    
                
    @Override
    @RolesRequired("COLLABORATOR")
    public void say(String text, ImageData image) throws JMSException, SampleChatException {
        try {
            BytesMessage message = applicationBeanSingleton.createBytesMessage();
            message.setStringProperty(ChatMessage.TYPE, ChatMessage.Type.MESSAGE.toString());
            message.setStringProperty(ChatMessage.NICK, getUserName());
            message.setStringProperty(ChatMessage.MESSAGE, text);
            if (image != null) {
                message.writeBytes(image.getImageBytes());
            }
            applicationBeanSingleton.publish(message);
        } catch (JMSException ex) {
            logger.error("Cannot publish message from " + getUserName(), ex);
            throw ex;
        }
    }

    @Override
    @RolesRequired("COLLABORATOR")
    public void logout() throws SampleChatException {
        try {
            if (getSubject() == null || !getSubject().isAuthenticated()) return;
            Message message = applicationBeanSingleton.createMessage();
            message.setStringProperty(ChatMessage.TYPE, ChatMessage.Type.COLLABORATOR_LIEVE.toString());
            message.setStringProperty(ChatMessage.NICK, getUserName());
            applicationBeanSingleton.publish(message);
            applicationBeanSingleton.unregisterSession(getSubject().getPrincipal().toString());
            getSubject().logout();
        } catch (Exception ex) {
            logger.error("Cannot logout user " + getUserName(), ex);
        }
    }

    @Override
    @RolesRequired("COLLABORATOR")
    public List getCollaborators() {
        return applicationBeanSingleton.getNickNameList();
    }

    @Override
    @RolesRequired("ADMIN")
    @PermissionsRequired("BAN")
    public void banUser(String selectedCollaborator) throws SampleChatException {
        applicationBeanSingleton.unregisterSession(selectedCollaborator);
    }

    @Override
    public void login(String uid, String pwd) throws SampleChatException {
        if (getSubject() != null && getSubject().isAuthenticated()) {
            throw new SampleChatException("already_authenticated", getSubject().getPrincipal());
        }
        UsernamePasswordToken token = new UsernamePasswordToken(uid, pwd);
        try {
            logger.info("Authenticate user " + uid);
            logger.info("Thread id " + Thread.currentThread().getId());
            subject = new Subject.Builder(applicationBeanSingleton.getSecurityManager()).buildSubject();
            getSubject().login(token);
            applicationBeanSingleton.registerSession(uid);
            Message message = applicationBeanSingleton.createMessage();
            message.setStringProperty(ChatMessage.TYPE, ChatMessage.Type.COLLABORATOR_ARRIVE.toString());
            message.setStringProperty(ChatMessage.NICK, uid);
            applicationBeanSingleton.publish(message);
        } catch (UnknownAccountException uae) {
            throw new SampleChatException("unknow_account", uid);
        } catch (IncorrectCredentialsException ice) {
            throw new SampleChatException("invalid_password", uid);
        } catch (AuthenticationException lae) {
            logger.error("Authentication exception", lae);
            throw new SampleChatException("cannot_auhtenticate", lae.getMessage());
        } catch (Exception e) {
            logger.error("Cannot accomplish authentication", e);
            throw new SampleChatException("application_error");
        }
    }

    @Override
    @RolesRequired("COMMON")
    public boolean hasPermissions(String... permissions) throws SampleChatException {
        Subject subject = SecurityUtils.getSubject();
        return subject.isPermittedAll(permissions);
    }

    public Subject getSubject() {
        return subject;
    }

    @PostConstruct
    public void postConstruct() {
        logger.info("Initialize, Thread id " + Thread.currentThread().getId());
    }

    @PostActivate
    public void postActivate() {
        subject = new Subject.Builder().buildSubject();
        logger.info("Initialize, Thread id " + Thread.currentThread().getId());
    }

    @PrePassivate
    public void prePassivate() {
        logger.info("Passivate, Thread id " + Thread.currentThread().getId());
    }

    @PreDestroy
    private void preDestroy() {
        try {
            logger.info("Destroy, Thread id " + Thread.currentThread().getId());
            logout();
        } catch (SampleChatException e) {
            logger.error(e);
        }
    }

}
