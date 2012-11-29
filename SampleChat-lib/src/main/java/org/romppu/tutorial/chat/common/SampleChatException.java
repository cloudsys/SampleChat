/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.common;

import javax.ejb.ApplicationException;

/**
 *
 * @author roman
 */
@ApplicationException(rollback = true)
public class SampleChatException extends Exception {
       
    private final Object[] params;
    
    public SampleChatException(String message) {
        super(message);
        this.params = null;
    }
    
    public SampleChatException(String message, Object... params) {
        super(message);
        this.params = params;
    }

    /**
     * @return the params
     */
    public Object[] getParams() {
        return params;
    }

}
