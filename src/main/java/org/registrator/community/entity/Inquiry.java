package org.registrator.community.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.registrator.community.enumeration.InquiryType;

@Entity
@Table(name = "inquiry_list")
public class Inquiry implements Serializable {
    
    private static final long serialVersionUID = 1L;    
    
    @Id
    @Column(name = "inquiry_list_id")
    @GeneratedValue
    private Integer inquiryId;

    @Column(name = "inquiry_type", nullable = false)
    @Enumerated(EnumType.STRING) 
    private InquiryType inquiryType;
    
    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    private User registrator;
    
    @ManyToOne
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;

    public Inquiry() {
        
    }
    
    public Inquiry(String inquiryType, Date date, User user, User registrator, Resource resource) {
        this.inquiryType = InquiryType.valueOf(inquiryType.toUpperCase());
        this.date = date;
        this.user = user;
        this.registrator = registrator;
        this.resource = resource;
    }

    public Integer getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(Integer inquiryId) {
        this.inquiryId = inquiryId;
    }

    public InquiryType getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = InquiryType.valueOf(inquiryType.toUpperCase());
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getRegistrator() {
        return registrator;
    }

    public void setRegistrator(User registrator) {
        this.registrator = registrator;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}

