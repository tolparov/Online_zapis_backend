package ru.alan.viewPerson.dto.user;

import ru.alan.viewPerson.entity.Role;

import java.io.Serializable;
import java.util.Set;

public class UserResponseDto implements Serializable {

	private Long id;

	private String email;

	private String login;

	private Role roles;

	public UserResponseDto() {
	}

	public UserResponseDto(Long id, String email, String login, Role roles) {
		this.id = id;
		this.email = email;
		this.login = login;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Role getRoles() {
		return roles;
	}

	public void setRoles(Role roles) {
		this.roles = roles;
	}
}
