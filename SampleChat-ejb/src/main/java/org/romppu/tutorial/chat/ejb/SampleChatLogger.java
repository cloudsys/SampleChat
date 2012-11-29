/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.ejb;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

/**
 *
 * @author roman
 */
public class SampleChatLogger {
    
    static {
        try {
            PropertyConfigurator.configure(new File("log4j.properties").toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }                
    };
    
    public static Logger getLogger(Class clazz) {
        return Logger.getLogger(clazz);
    }
 }
