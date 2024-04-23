package ru.alan.viewPerson.dto.user;

import org.hibernate.validator.constraints.Length;
import ru.alan.viewPerson.dto.validation.OnCreate;
import ru.alan.viewPerson.dto.validation.OnPasswordUpdate;

import javax.validation.constraints.NotBlank;

public class UserResetPasswordDto {
	private String email;
	@NotBlank(message = "Password must not be null!", groups = {OnCreate.class, OnPasswordUpdate.class})
	@Length(message = "Password must be at least 8 symbols long!", min = 8,
			groups = {OnCreate.class, OnPasswordUpdate.class})
	@Length(message = "Password must be less than 255 symbols long!", max = 255,
			groups = {OnCreate.class, OnPasswordUpdate.class})
	private String newPassword;

	public UserResetPasswordDto() {
	}

	public UserResetPasswordDto(String email, String newPassword) {
		this.email = email;
		this.newPassword = newPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
