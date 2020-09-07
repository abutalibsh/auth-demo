package com.inma.invest.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = { "email" }) })
public class User  {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "assigned-sequence", strategy = "com.inma.invest.utils.StringSequenceIdentifier", parameters = {
			@org.hibernate.annotations.Parameter(name = "sequence_name", value = "user_seq"),
			@org.hibernate.annotations.Parameter(name = "sequence_prefix", value = "AC"), })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assigned-sequence")
	private String id;

	@NotBlank
	@Size(max = 40)
	private String name;

	@NotBlank
	@Size(max = 40)
	private String username;

	@NaturalId(mutable = true)
	@NotBlank
	@Size(max = 40)
	@Email
	private String email;

	@NotBlank
	@Size(max = 100)
	private String password;

	private String bio;
	@Column(length = 511)
	private String image;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();



	public User() {

	}

	public User(String name, String username, String email, String password) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public void update(String email, String username, String bio, String image) {
		if (!"".equals(email)) {
			this.email = email;
		}

		if (!"".equals(username)) {
			this.username = username;
		}

		if (!"".equals(bio)) {
			this.bio = bio;
		}

		if (!"".equals(image)) {
			this.image = image;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	

}