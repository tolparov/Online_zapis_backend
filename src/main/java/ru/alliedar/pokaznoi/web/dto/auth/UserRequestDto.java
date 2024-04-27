package ru.alliedar.pokaznoi.web.dto.auth;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;
import ru.alliedar.pokaznoi.web.dto.validation.OnPasswordUpdate;
import ru.alliedar.pokaznoi.web.dto.validation.OnUpdate;

import java.io.Serializable;

public class UserRequestDto implements Serializable {

    private Long id;

    @NotBlank(message = "Email must not be null!", groups = {OnCreate.class, OnUpdate.class})
    @Email(message = "Email is not valid!", groups = {OnCreate.class, OnUpdate.class})
    @Length(message = "Email must be less than 255 symbols long!", max = 255,
            groups = {OnCreate.class, OnUpdate.class})
    private String email;

    @NotBlank(message = "Firstname must not be null!", groups = {OnCreate.class, OnUpdate.class})
    @Length(message = "Firstname must be less than 255 symbols long!", max = 255,
            groups = {OnCreate.class, OnUpdate.class})
    private String login;

    @NotBlank(message = "Password must not be null!", groups = {OnCreate.class, OnPasswordUpdate.class})
    @Length(message = "Password must be at least 8 symbols long!", min = 8,
            groups = {OnCreate.class, OnPasswordUpdate.class})
    @Length(message = "Password must be less than 255 symbols long!", max = 255,
            groups = {OnCreate.class, OnPasswordUpdate.class})
    private String password;

    @NotBlank(message = "Confirmation must not be null!", groups = {OnCreate.class, OnPasswordUpdate.class})
    @Length(message = "Confirmation must be less than 255 symbols long!", max = 255,
            groups = {OnCreate.class, OnPasswordUpdate.class})
    private String passwordConfirmation;

    public UserRequestDto() {
    }

    public UserRequestDto(Long id, String email, String login, String password, String passwordConfirmation) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    @AssertTrue(message = "Password and confirmation must match!", groups = {OnCreate.class, OnPasswordUpdate.class})
    public boolean isPasswordConfirmed() {
        return password != null && password.equals(passwordConfirmation);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
