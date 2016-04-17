package org.registrator.community.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.registrator.community.enumeration.UserStatus;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private Integer userId;

    @Column(name = "login", length = 30, unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name", nullable = true)
    private String middleName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Address> address = new ArrayList<Address>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PassportInfo> passport = new ArrayList<PassportInfo>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<WillDocument> willDocument = new ArrayList<WillDocument>();
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OtherDocuments> otherDocuments = new ArrayList<OtherDocuments>();

    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.ALL})
    @JoinTable(name="registrator_owner",
        joinColumns={@JoinColumn(name="owner_id")},
        inverseJoinColumns={@JoinColumn(name="registrator_id")})
    private Set<User> registrators = new HashSet<User>();
 
    @ManyToMany(fetch = FetchType.LAZY, mappedBy="registrators")
    private Set<User> owners = new HashSet<User>();
    
    @Column(name = "phonenumber", nullable = true)
    private String phoneNumber;
    
    @Column(name = "date_of_accession", nullable = false)
    private Date dateOfAccession;
    
    @ManyToOne
    @JoinColumn(name = "territorialCommunity_id", nullable = false)
    private TerritorialCommunity territorialCommunity;
    
    
    @Column(name = "attempts")
 	private int attempts=0;
     
     @Column(name = "last_modified")
 	private Timestamp lastModified;
     
     @Column(name = "enabled")
     private int enabled=1;
     
     @Column(name = "account_non_expired")
     private int accountNonExpired=1;
     
     @Column(name = "account_non_locked")
     private int accountNonLocked=1;
     
     @Column(name = "credentials_non_expired")
     private int credentialsNonExpired=1;
    
    
    
    
    public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(int accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public int getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(int accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public int getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(int credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public TerritorialCommunity getTerritorialCommunity() {
        return territorialCommunity;
    }

    public void setTerritorialCommunity(TerritorialCommunity territorialCommunity) {
        this.territorialCommunity = territorialCommunity;
    }

    public User() {

    }
    
    

    public User(String login, String email, UserStatus status) {
        super();
        this.login = login;
        this.email = email;
        this.status = status;
    }

    public User(String login, String email) {
        super();
        this.login = login;
        this.email = email;
    }

    public User(String login, String password, Role role, String firstName, String lastName, String middleName,
            String email, String status) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.status = UserStatus.valueOf(status.toUpperCase());
    }
    
    public User(String login, String password, Role role, String firstName, String lastName, String middleName,
            String email, String status, String phoneNumber) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.status = UserStatus.valueOf(status.toUpperCase());
        this.phoneNumber = phoneNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<PassportInfo> getPassport() {
        return passport;
    }

    public void setPassport(List<PassportInfo> passport) {
        this.passport = passport;
    }

    public List<WillDocument> getWillDocument() {
        return willDocument;
    }

    public void setWillDocument(List<WillDocument> willDocument) {
        this.willDocument = willDocument;
    }

    public List<OtherDocuments> getOtherDocuments() {
        return otherDocuments;
    }

    public void setOtherDocuments(List<OtherDocuments> otherDocuments) {
        this.otherDocuments = otherDocuments;
    }

    public Set<User> getRegistrators() {
        return registrators;
    }

    public void setRegistrators(Set<User> registrators) {
        this.registrators = registrators;
    }

    public Set<User> getOwners() {
        return owners;
    }

    public void setOwners(Set<User> owners) {
        this.owners = owners;
    }

    public void setPasswordHash(String passwordHash){
        this.password = passwordHash;
    }

    public Date getDateOfAccession() {
        return dateOfAccession;
    }

    public void setDateOfAccession(Date dateOfAccession) {
        this.dateOfAccession = dateOfAccession;
    }

    @Override
    public int hashCode() {
        if (userId == null) {
            return super.hashCode();
        }
        return userId.hashCode();
    }

    /**
     * Equality for User is dependent on database identity. It means that two different instances of User.class will be
     * equal only if their id's in database are equal.
     * @param obj the object to compare with.
     * @return true if objects are equal,
     *         false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof User))
            return false;
        User that = (User) obj;
        if ((this.getUserId() == null) || (that.getUserId() == null)) return false;
        return this.getUserId().equals(that.getUserId());
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(getFirstName());
        joiner.add(getMiddleName());
        joiner.add(getLastName());
        return joiner.toString();
    }
}
