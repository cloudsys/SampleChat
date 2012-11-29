/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.ejb;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author roman
 */
public class PersistenceUtil {
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    
    public static String getTimestamp() {
        return TIMESTAMP_FORMAT.format(new Date());
    }
    
    public static String getTimestamp(Date date) {
        return TIMESTAMP_FORMAT.format(date);
    }
}
