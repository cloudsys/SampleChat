package org.romppu.tutorial.chat.ejb.security;

import org.apache.shiro.subject.Subject;

/**
 * Created with IntelliJ IDEA.
 * User: roman
 * Date: 28.11.2012
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
public interface SecuredSubjectOwner {
    public Subject getSubject();
}
