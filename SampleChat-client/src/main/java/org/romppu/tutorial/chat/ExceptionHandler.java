/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat;

import org.romppu.tutorial.chat.common.SampleChatException;

import javax.swing.*;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roman
 */
public class ExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(SampleChatApplication.class.getName());
    
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(ExceptionHandler.class.getName());
            
    public static void handleException(Exception ex) {
        if (ex instanceof SampleChatException) {
            String message = RESOURCE_BUNDLE.getString(ex.getMessage());
            String formattedMessage = MessageFormat.format(message, ((SampleChatException)ex).getParams());
            LOGGER.info(formattedMessage);
            JOptionPane.showMessageDialog(null, formattedMessage, "Message", JOptionPane.INFORMATION_MESSAGE);
        } else {
            LOGGER.log(Level.SEVERE, "Application Error", ex);
            JOptionPane.showMessageDialog(null, "Application error occurred. See log messages for more details", "Application error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
