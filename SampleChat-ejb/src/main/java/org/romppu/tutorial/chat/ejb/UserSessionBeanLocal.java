package org.romppu.tutorial.chat.ejb;

import org.apache.shiro.subject.Subject;

import javax.ejb.Local;

/**
 * Created with IntelliJ IDEA.
 * User: roman
 * Date: 28.11.2012
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface UserSessionBeanLocal {
    public Subject getSubject();
}
