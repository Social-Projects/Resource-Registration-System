package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.registrator.community.enumeration.RoleType;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "role_id")
    @GeneratedValue
    private Integer roleId;

	
    @Column(name = "type", unique = true, nullable = false)
	@Enumerated(EnumType.STRING) 
	private RoleType type;
    
    @Column(name = "description", nullable = false)
    private String description;

    public Role(){
    }
    
    public Role(RoleType type, String description) {
		this.type = type;
		this.description = description;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public RoleType getType() {
		return type;
	}

	public void setType(RoleType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
	@Override 
	public String toString() {
		return  type.toString();
	}
}


