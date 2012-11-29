/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.ejb.security;

import org.romppu.tutorial.chat.common.SampleChatException;
import org.romppu.tutorial.chat.ejb.ICLogger;
import org.romppu.tutorial.chat.ejb.UserSessionBean;
import org.romppu.tutorial.chat.ejb.annotation.PermissionsRequired;
import org.romppu.tutorial.chat.ejb.annotation.RolesRequired;
import org.romppu.tutorial.chat.ejb.annotation.Secured;
import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author roman
 */
@Secured
@Interceptor
public class ChatSecurityInterceptor implements Serializable {
 
    private static final Logger logger = ICLogger.getLogger(ChatSecurityInterceptor.class);

    public ChatSecurityInterceptor() {

    }
    
    @AroundInvoke
    public Object checkPermissions(InvocationContext invocationContext) throws Exception {
        Subject subject = ((UserSessionBean)invocationContext.getTarget()).getSubject();
        if (subject == null)
            return invocationContext.proceed();

        PermissionsRequired permissionsRequired = invocationContext.getMethod().getAnnotation(PermissionsRequired.class);
        RolesRequired rolesRequired = invocationContext.getMethod().getAnnotation(RolesRequired.class);
        if (permissionsRequired == null && rolesRequired == null) {
            //Invocation of unsecured method
            return invocationContext.proceed();
        } 

        if (permissionsRequired != null && permissionsRequired.value() != null) {
            if (!subject.isPermittedAll(permissionsRequired.value()))
                throw new SampleChatException("access_denied", Arrays.asList(permissionsRequired.value()));
        }
        if (rolesRequired != null && rolesRequired.value() != null) {
            List<String> requiredRoles = Arrays.asList(rolesRequired.value());
            if (!subject.hasAllRoles(requiredRoles)) {
                logger.warn(MessageFormat.format("Access for {0} denied by role", subject.getPrincipal().toString()));
                throw new SampleChatException("access_by_role_denied", requiredRoles);
            }
        }

        return invocationContext.proceed();
    }
}
