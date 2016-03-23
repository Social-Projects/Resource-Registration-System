package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "passport_data")
public class PassportInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "passport_data_id")
    @GeneratedValue
    private Integer passportId;

	@ManyToOne(fetch=FetchType.EAGER)
	@JsonBackReference
    @JoinColumn(name="user_id", nullable = false)  
    private User user;

    @Column(name = "seria", nullable = false)
    private String seria;
    
    @Column(name = "number", nullable = false)
    private Integer number;
    
    @Column(name = "published_by_data", nullable = false)
    private String publishedByData;
    
    public PassportInfo() {
    	
    }
    
    public PassportInfo(User user, String seria, Integer number, String publishedByData) {
		this.user = user;
		this.seria = seria;
		this.number = number;
		this.publishedByData = publishedByData;
	}
    
	public Integer getPassportId() {
		return passportId;
	}

	public void setPassportId(Integer passportId) {
		this.passportId = passportId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSeria() {
		return seria;
	}

	public void setSeria(String seria) {
		this.seria = seria;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getPublishedByData() {
		return publishedByData;
	}

	public void setPublishedByData(String publishedByData) {
		this.publishedByData = publishedByData;
	}   
}
