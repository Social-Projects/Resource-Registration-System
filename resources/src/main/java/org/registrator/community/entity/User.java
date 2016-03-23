package org.registrator.community.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	
	@Column(name = "login",unique = true, nullable = false)	
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
	
	@Column(name = "middle_name", nullable = false)
	private String middleName;
	
	@Column(name = "email",unique = true, nullable = false)
	private String email;
	
	@Column(name = "status", nullable = false, columnDefinition = "ENUM('BLOCK','ACTIVE','INACTIVE')")
	@Enumerated(EnumType.STRING)
	private UserStatus status;
	
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	@JsonManagedReference
	private List<Address> address = new ArrayList<Address>();
	
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	@JsonManagedReference
	private List<PassportInfo> passport = new ArrayList<PassportInfo>();
	
	public User() {
		
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
	
	
	/*public User(String firstName, String middleName, String lastName, Role role,
			String email, String login, String password, String status) {
		this.login = login;
		this.password = password;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.email = email;		
		this.status = UserStatus.valueOf(status.toUpperCase());
	}*/

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
		this.address=address;
	}
	
	public List<PassportInfo> getPassport() {
		return passport;
	}
	
	public void setPassport(List<PassportInfo> passport) {
		this.passport=passport;
	}
	
}
