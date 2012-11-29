/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.common;

import java.io.Serializable;

/**
 *
 * @author roman
 */
public class ChatMessage implements Serializable {

    public static final String NICK = "nick";
    public static final String MESSAGE = "message";
    public static final String TYPE = "type";
    
    public enum Type {
        MESSAGE,
        COLLABORATOR_ARRIVE,
        COLLABORATOR_LIEVE
    }
}
