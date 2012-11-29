/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.gui.swing;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 *
 * @author roman
 */
public class SampleChatFrameBean implements Serializable {

    private PropertyChangeSupport propertySupport;

    public static final String PROP_SELECTEDCOLLABORATOR = "selectedCollaborator";
    public static final String PROP_LOGGEDIN = "loggedIn";
    public static final String PROP_CHATMESSAGE = "chatMessage";

    private String selectedCollaborator;
    private String chatMessage;
    private boolean loggedIn;

    public SampleChatFrameBean() {
        propertySupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    
    /**
     * Get the value of loggedIn
     *
     * @return the value of loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Set the value of loggedIn
     *
     * @param loggedIn new value of loggedIn
     */
    public void setLoggedIn(boolean loggedIn) {
        boolean oldLoggedIn = this.loggedIn;
        this.loggedIn = loggedIn;
        propertySupport.firePropertyChange(PROP_LOGGEDIN, oldLoggedIn, loggedIn);
    }

    /**
     * Get the value of chatMessage
     *
     * @return the value of chatMessage
     */
    public String getChatMessage() {
        return chatMessage;
    }

    /**
     * Set the value of chatMessage
     *
     * @param chatMessage new value of chatMessage
     */
    public void setChatMessage(String chatMessage) {
        String oldChatMessage = this.chatMessage;
        this.chatMessage = chatMessage;
        propertySupport.firePropertyChange(PROP_CHATMESSAGE, oldChatMessage, chatMessage);
    }

    /**
     * Get the value of selectedCollaborator
     *
     * @return the value of selectedCollaborator
     */
    public String getSelectedCollaborator() {
        return selectedCollaborator;
    }

    /**
     * Set the value of selectedCollaborator
     *
     * @param selectedCollaborator new value of selectedCollaborator
     */
    public void setSelectedCollaborator(String selectedCollaborator) {
        String oldSelectedCollaborator = this.selectedCollaborator;
        this.selectedCollaborator = selectedCollaborator;
        propertySupport.firePropertyChange(PROP_SELECTEDCOLLABORATOR, oldSelectedCollaborator, selectedCollaborator);
    }
}
