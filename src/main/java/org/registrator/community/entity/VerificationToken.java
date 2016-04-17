package org.registrator.community.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.registrator.community.enumeration.TokenType;

@Entity
@Table(name = "verification_token")
public class VerificationToken {
	
	@Id
	@Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
	@Column(name = "token", nullable = false)
    private String token;
    
	@Column(name = "user_Login", nullable = true)
	private String userLogin;
	
	@Column(name = "user_email", nullable = false)
    private String userEmail;
    
	@Column(name = "expiry_date", nullable = false)
    private Date expiryDate;
	
	@Column(name = "token_type", nullable = false)
    @Enumerated(EnumType.STRING)
	private TokenType tokenType;
	
	@Column(name = "base_Link", nullable = true)
	private String baseLink;

	public VerificationToken() {
	}

	public VerificationToken(String token, String userEmail, Date expiryDate) {
		this.token = token;
		this.userEmail = userEmail;
		this.expiryDate = expiryDate;
	}
	
	public VerificationToken(String token, String userEmail, Date expiryDate,
            TokenType tokenType) {
        this.token = token;
        this.userEmail = userEmail;
        this.expiryDate = expiryDate;
        this.tokenType = tokenType;
    }
	
	public VerificationToken(String token, String login, String userEmail, Date expiryDate,
			TokenType tokenType) {
		this.token = token;
		this.userLogin = login;
		this.userEmail = userEmail;
		this.expiryDate = expiryDate;
		this.tokenType = tokenType;
	}

	public VerificationToken(String token, String userEmail, Date expiryDate, TokenType tokenType, String baseLink) {
        this.token = token;
        this.userEmail = userEmail;
        this.expiryDate = expiryDate;
        this.tokenType = tokenType;
        this.baseLink = baseLink;
    }

    public VerificationToken(String token, String userLogin, String userEmail, Date expiryDate, TokenType tokenType,
            String baseLink) {
        this.token = token;
        this.userLogin = userLogin;
        this.userEmail = userEmail;
        this.expiryDate = expiryDate;
        this.tokenType = tokenType;
        this.baseLink = baseLink;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

    public String getBaseLink() {
        return baseLink;
    }

    public void setBaseLink(String baseLink) {
        this.baseLink = baseLink;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
    
}
