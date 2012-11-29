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
@Table(name = "T_SURRO")
@XmlRootElement
public class TSurro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "SURRO_TABLE_NME")
    private String surroTableNme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SURRO_VALUE_INT")
    private int surroValueInt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "SURRO_UTS")
    private String surroUts;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "SURRO_UUI")
    private String surroUui;

    public TSurro() {
    }

    public TSurro(String surroTableNme) {
        this.surroTableNme = surroTableNme;
    }

    public TSurro(String surroTableNme, int surroValueInt, String surroUts, String surroUui) {
        this.surroTableNme = surroTableNme;
        this.surroValueInt = surroValueInt;
        this.surroUts = surroUts;
        this.surroUui = surroUui;
    }

    public String getSurroTableNme() {
        return surroTableNme;
    }

    public void setSurroTableNme(String surroTableNme) {
        this.surroTableNme = surroTableNme;
    }

    public int getSurroValueInt() {
        return surroValueInt;
    }

    public void setSurroValueInt(int surroValueInt) {
        this.surroValueInt = surroValueInt;
    }

    public String getSurroUts() {
        return surroUts;
    }

    public void setSurroUts(String surroUts) {
        this.surroUts = surroUts;
    }

    public String getSurroUui() {
        return surroUui;
    }

    public void setSurroUui(String surroUui) {
        this.surroUui = surroUui;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (surroTableNme != null ? surroTableNme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TSurro)) {
            return false;
        }
        TSurro other = (TSurro) object;
        if ((this.surroTableNme == null && other.surroTableNme != null) || (this.surroTableNme != null && !this.surroTableNme.equals(other.surroTableNme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.romppu.tutorial.chat.entity.TSurro[ surroTableNme=" + surroTableNme + " ]";
    }
    
}
