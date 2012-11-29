/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.gui.swing;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author roman
 */
public class LoginBean implements Serializable {

    private PropertyChangeSupport propertySupport;

    public LoginBean() {
        propertySupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    private String userName = "";
    public static final String PROP_USERNAME = "userName";

    /**
     * Get the value of userName
     *
     * @return the value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the value of userName
     *
     * @param userName new value of userName
     */
    public void setUserName(String userName) {
        String oldUserName = this.userName;
        this.userName = userName;
        propertySupport.firePropertyChange(PROP_USERNAME, oldUserName, userName);
    }
    private String password = "";
    public static final String PROP_PASSWORD = "password";

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        String oldPassword = this.password;
        this.password = password;
        propertySupport.firePropertyChange(PROP_PASSWORD, oldPassword, password);
    }
}
