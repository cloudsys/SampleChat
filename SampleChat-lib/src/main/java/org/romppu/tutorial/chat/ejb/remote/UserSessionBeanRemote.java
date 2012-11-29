/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.ejb.remote;

import org.romppu.tutorial.chat.common.ImageData;
import org.romppu.tutorial.chat.common.SampleChatException;

import javax.ejb.Remote;
import javax.jms.JMSException;
import java.util.List;

/**
 *
 * @author roman
 */
@Remote
public interface UserSessionBeanRemote {
    public String getLoginName() throws SampleChatException;
    public List getCollaborators();
    public void say(String text, ImageData image) throws JMSException, SampleChatException;
    public void banUser(String selectedCollaborator) throws SampleChatException;
    public void login(String uid, String pwd) throws SampleChatException;
    public void logout() throws SampleChatException;
    public boolean hasPermissions(String... permissions) throws SampleChatException;
}
