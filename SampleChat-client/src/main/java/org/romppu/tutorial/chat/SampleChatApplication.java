package org.romppu.tutorial.chat;

import org.romppu.tutorial.chat.gui.swing.SampleChatFrame;
import org.romppu.tutorial.chat.gui.swing.LoginDialog;
import org.romppu.tutorial.chat.ejb.remote.UserSessionBeanRemote;

import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Enterprise Application Client main class.
 *
 */
public class SampleChatApplication {
    
    private static final Logger logger = Logger.getLogger(SampleChatApplication.class.getName());
    private final Properties properties;

    private final SampleChatFrame frame;

    @EJB
    public static UserSessionBeanRemote userSessionBean;

    private SampleChatApplication() throws IOException, NamingException, JMSException {
        properties = new Properties();
        properties.load(getClass().getResourceAsStream("SampleChatApplication.properties"));
        frame = new SampleChatFrame();
        new SimpleTopicSubscriber(
                getProperty("jms.topic.name"),
                getProperty("jms.topic.connectionFactory"),
                frame);

        LoginDialog dialog = LoginDialog.execute();
        if (dialog.isCancelPressed()) return;

        frame.setVisible(true);
    }

    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
    
    public static void main( String[] args ) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }        
            new SampleChatApplication();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the userSessionBean
     */
    public UserSessionBeanRemote getUserSessionBean() {
        return userSessionBean;
    }
}
