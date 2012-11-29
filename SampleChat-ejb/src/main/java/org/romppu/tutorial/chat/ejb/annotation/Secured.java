/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.ejb.annotation;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 *
 * @author roman
 */
@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.TYPE
})
public @interface Secured {
    
}
