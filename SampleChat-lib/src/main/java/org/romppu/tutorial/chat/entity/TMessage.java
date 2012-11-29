/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author roman
 */
@Entity
@Table(name = "T_MESSAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TMessage.findAll", query = "SELECT t FROM TMessage t"),
    @NamedQuery(name = "TMessage.findByMsgId", query = "SELECT t FROM TMessage t WHERE t.msgId = :msgId"),
    @NamedQuery(name = "TMessage.findByMsgSenderNme", query = "SELECT t FROM TMessage t WHERE t.msgSenderNme = :msgSenderNme")
})
public class TMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MSG_ID")
    private Integer msgId;
    @Size(max = 25)
    @Column(name = "MSG_CUI")
    private String msgSenderNme;
    @Size(max = 17)
    @Column(name = "MSG_CTS")
    private String msgSendDte;
    @Size(max = 255)
    @Column(name = "MSG_MESSAGE_CHR")
    private String msgMessageChr;
    @Lob
    @Column(name = "MSG_IMG_BLO")
    private byte[] msgImgBlo;

    public TMessage() {
    }

    public TMessage(Integer msgId) {
        this.msgId = msgId;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getMsgSenderNme() {
        return msgSenderNme;
    }

    public void setMsgSenderNme(String msgSenderNme) {
        this.msgSenderNme = msgSenderNme;
    }

    public String getMsgSendDte() {
        return msgSendDte;
    }

    public void setMsgSendDte(String msgSendDte) {
        this.msgSendDte = msgSendDte;
    }

    public String getMsgMessageChr() {
        return msgMessageChr;
    }

    public void setMsgMessageChr(String msgMessageChr) {
        this.msgMessageChr = msgMessageChr;
    }

    public byte[] getMsgImgBlo() {
        return msgImgBlo;
    }

    public void setMsgImgBlo(byte[] msgImgBlo) {
        this.msgImgBlo = msgImgBlo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msgId != null ? msgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TMessage)) {
            return false;
        }
        TMessage other = (TMessage) object;
        if ((this.msgId == null && other.msgId != null) || (this.msgId != null && !this.msgId.equals(other.msgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.romppu.tutorial.chat.entity.TMessage[ msgId=" + msgId + " ]";
    }
    
}
