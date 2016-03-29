package org.registrator.community.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "will_data")
public class WillDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "will_id")
    @GeneratedValue
    private Integer willId;

    @ManyToOne(fetch=FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name="user_id", nullable = false) 
    private User user;
    
    @Column(name = "accession_date", nullable = false)
    private Date accessionDate;
    
    @Column(name = "comment")
    private String comment;
    
    public WillDocument() {
        
    }

    public Integer getWillId() {
        return willId;
    }

    public void setWillId(Integer willId) {
        this.willId = willId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getAccessionDate() {
        return accessionDate;
    }

    public void setAccessionDate(Date accessionDate) {
        this.accessionDate = accessionDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
