/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.gui.swing;

import org.jdesktop.beansbinding.*;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.romppu.tutorial.chat.ExceptionHandler;
import org.romppu.tutorial.chat.SampleChatApplication;
import org.romppu.tutorial.chat.SimpleTopicSubscriber;
import org.romppu.tutorial.chat.common.ImageData;
import org.romppu.tutorial.chat.common.SampleChatException;
import org.romppu.tutorial.chat.common.Tools;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 * @author roman
 */
public class SampleChatFrame extends javax.swing.JFrame implements SimpleTopicSubscriber.TopicListener {

    private static final String TITLE = "Chat ({0})";
    private static final Logger logger = Logger.getLogger(SampleChatFrame.class.getName());
    private final StyleContext sc = new StyleContext();
    private final DefaultStyledDocument doc = new DefaultStyledDocument(sc);
    private final Style textStyle = sc.addStyle("TextStyle", null);
    private final Style nickNameStyle = sc.addStyle("NickName", null);
    private final DefaultListModel collaboratorsListModel = new DefaultListModel();
    /**
     * Creates new form SampleChatFrame
     */
    public SampleChatFrame() throws NamingException, JMSException {
        initComponents();
        chatPane.setDocument(doc);
        nickNameStyle.addAttribute(StyleConstants.Foreground, Color.BLUE);
        nickNameStyle.addAttribute(StyleConstants.FontSize, new Integer(12));
        nickNameStyle.addAttribute(StyleConstants.FontFamily, "serif");
        nickNameStyle.addAttribute(StyleConstants.Bold, new Boolean(true));
        textStyle.addAttribute(StyleConstants.Foreground, Color.darkGray);
        textStyle.addAttribute(StyleConstants.FontSize, new Integer(12));
        textStyle.addAttribute(StyleConstants.FontFamily, "serif");
        textStyle.addAttribute(StyleConstants.Bold, new Boolean(false));
        collaboratorsList.setModel(collaboratorsListModel);
        Rectangle bounds = Tools.restoreBounds(Preferences.userNodeForPackage(getClass()));
        if (bounds != null) {
            setBounds(bounds);
        } else {
            setSize(400, 200);
            setLocationRelativeTo(null);
        }
    }

    @Override
    public void onMessage(String nickName, String text, Image img) {
        try {
            doc.insertString(0, "\n", null);
            if (text != null && !text.isEmpty()) {
                doc.insertString(0, text, textStyle);
            }
            if (img != null) {
                doc.insertString(0, "\n", null);
                Style style = doc.addStyle("StyleName", null);
                StyleConstants.setIcon(style, new ImageIcon(img));
                doc.insertString(0, " ", style);
            }
            doc.insertString(0, nickName + ":", nickNameStyle);
        } catch (BadLocationException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    @Override
    public void onCollaboratorArrive(String nickName) {
        try {
            if (!nickName.equals(SampleChatApplication.userSessionBean.getLoginName())) {
                collaboratorsListModel.add(0, nickName);
            }
        } catch (SampleChatException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    @Override
    public void onCollaboratorLieve(String nickName) {
        try {
            collaboratorsListModel.removeElement(nickName);
            if (SampleChatApplication.userSessionBean.getLoginName().equals(nickName)) {
                dispose();
            }
        } catch (SampleChatException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    void initSession() {
        try {
            String nickName = SampleChatApplication.userSessionBean.getLoginName();
            for (Object collaborator : SampleChatApplication.userSessionBean.getCollaborators()) {
                collaboratorsListModel.addElement(collaborator);
            }
            sampleChatFrameBean.setLoggedIn(true);
            setTitle(MessageFormat.format(TITLE, nickName));
            banButton.setEnabled(SampleChatApplication.userSessionBean.hasPermissions("BAN"));
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    void say() {
        try {
            SampleChatApplication.userSessionBean.say(sampleChatFrameBean.getChatMessage(), null);
            sampleChatFrameBean.setChatMessage("");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void sendImage() {
        final JFileChooser fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Image files", "png", "jpg", "gif");
        fc.addChoosableFileFilter(filter);

        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                SampleChatApplication.userSessionBean.say(sampleChatFrameBean.getChatMessage(), ImageData.createInstance(file));
                sampleChatFrameBean.setChatMessage("");
            } catch (Exception ex) {
                ExceptionHandler.handleException(ex);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        ResourceBundle bundle = ResourceBundle.getBundle("org.romppu.tutorial.chat.gui.swing.ChatFrameBundle");
        contentPanel = new JPanel();
        centerPanel = new JPanel();
        scrollPane = new JScrollPane();
        chatPane = new JTextPane();
        chatMessageField = new JTextField();
        eastPanel = new JPanel();
        buttonsPanel = new JPanel();
        sendButton = new JButton();
        selectImageButton = new JButton();
        collaboratorScrollPane = new JScrollPane();
        collaboratorsList = new JList();
        toolsPanel = new JPanel();
        banButton = new JButton();
        sampleChatFrameBean = new SampleChatFrameBean();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(bundle.getString("IntranetChatFrame.title"));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClosing(e);
            }
            @Override
            public void windowOpened(WindowEvent e) {
                onWindowOpened(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== contentPanel ========
        {
            contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            contentPanel.setLayout(new BorderLayout(5, 5));

            //======== centerPanel ========
            {
                centerPanel.setLayout(new BorderLayout(5, 5));

                //======== scrollPane ========
                {

                    //---- chatPane ----
                    chatPane.setEditable(false);
                    scrollPane.setViewportView(chatPane);
                }
                centerPanel.add(scrollPane, BorderLayout.CENTER);

                //---- chatMessageField ----
                chatMessageField.setPreferredSize(new Dimension(6, 25));
                chatMessageField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        chatMessageFieldKeyPressed(e);
                    }
                });
                centerPanel.add(chatMessageField, BorderLayout.NORTH);
            }
            contentPanel.add(centerPanel, BorderLayout.CENTER);

            //======== eastPanel ========
            {
                eastPanel.setPreferredSize(new Dimension(150, 100));
                eastPanel.setLayout(new BorderLayout(5, 5));

                //======== buttonsPanel ========
                {
                    buttonsPanel.setPreferredSize(new Dimension(470, 25));
                    buttonsPanel.setLayout(new BorderLayout());

                    //---- sendButton ----
                    sendButton.setText(bundle.getString("IntranetChatFrame.sendButton.text"));
                    sendButton.setMaximumSize(new Dimension(57, 25));
                    sendButton.setMinimumSize(new Dimension(57, 25));
                    sendButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            sendButtonActionPerformed(e);
                        }
                    });
                    buttonsPanel.add(sendButton, BorderLayout.CENTER);

                    //---- selectImageButton ----
                    selectImageButton.setIcon(new ImageIcon(getClass().getResource("/icon/picture_add.png")));
                    selectImageButton.setMargin(new Insets(2, 2, 2, 2));
                    selectImageButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            selectImageButtonActionPerformed(e);
                        }
                    });
                    buttonsPanel.add(selectImageButton, BorderLayout.LINE_END);
                }
                eastPanel.add(buttonsPanel, BorderLayout.NORTH);

                //======== collaboratorScrollPane ========
                {

                    //---- collaboratorsList ----
                    collaboratorsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    collaboratorsList.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            collaboratorsListMouseClicked(e);
                        }
                    });
                    collaboratorScrollPane.setViewportView(collaboratorsList);
                }
                eastPanel.add(collaboratorScrollPane, BorderLayout.CENTER);

                //======== toolsPanel ========
                {
                    toolsPanel.setLayout(new BorderLayout());

                    //---- banButton ----
                    banButton.setText(bundle.getString("IntranetChatFrame.banButton.text"));
                    banButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            banButtonActionPerformed(e);
                        }
                    });
                    toolsPanel.add(banButton, BorderLayout.CENTER);
                }
                eastPanel.add(toolsPanel, BorderLayout.PAGE_END);
            }
            contentPanel.add(eastPanel, BorderLayout.EAST);
        }
        contentPane.add(contentPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

        //---- bindings ----
        bindingGroup = new BindingGroup();
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            sampleChatFrameBean, BeanProperty.create("chatMessage"),
            chatMessageField, BeanProperty.create("text")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            collaboratorsList, BeanProperty.create("selectedElement"),
            sampleChatFrameBean, BeanProperty.create("selectedCollaborator")));
        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        say();
    }//GEN-LAST:event_sendButtonActionPerformed

    private void onWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_onWindowOpened
        initSession();
    }//GEN-LAST:event_onWindowOpened

    private void selectImageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectImageButtonActionPerformed
        sendImage();
    }//GEN-LAST:event_selectImageButtonActionPerformed

    private void onWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_onWindowClosing
        try {
            SampleChatApplication.userSessionBean.logout();
            Tools.storeBounds(Preferences.userNodeForPackage(getClass()), getBounds());
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        }

    }//GEN-LAST:event_onWindowClosing

    private void collaboratorsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_collaboratorsListMouseClicked
        if (evt.getClickCount() > 1) {
            sampleChatFrameBean.setChatMessage(collaboratorsList.getSelectedValue() + ", ");
            chatMessageField.grabFocus();
            chatMessageField.setCaretPosition(sampleChatFrameBean.getChatMessage().length());
        }
    }//GEN-LAST:event_collaboratorsListMouseClicked

    private void chatMessageFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chatMessageFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            say();
        }
    }//GEN-LAST:event_chatMessageFieldKeyPressed

    private void banButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banButtonActionPerformed
        try {
            if (sampleChatFrameBean.getSelectedCollaborator() == null) {
                JOptionPane.showMessageDialog(this, "Select collaborator from list");
                return;
            }
            SampleChatApplication.userSessionBean.banUser(sampleChatFrameBean.getSelectedCollaborator());
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        }
    }//GEN-LAST:event_banButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel contentPanel;
    private JPanel centerPanel;
    private JScrollPane scrollPane;
    private JTextPane chatPane;
    private JTextField chatMessageField;
    private JPanel eastPanel;
    private JPanel buttonsPanel;
    private JButton sendButton;
    private JButton selectImageButton;
    private JScrollPane collaboratorScrollPane;
    private JList collaboratorsList;
    private JPanel toolsPanel;
    private JButton banButton;
    private SampleChatFrameBean sampleChatFrameBean;
    private BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
