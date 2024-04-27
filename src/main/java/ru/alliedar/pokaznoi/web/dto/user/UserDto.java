package ru.alliedar.pokaznoi.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;
import ru.alliedar.pokaznoi.web.dto.validation.OnUpdate;


@Data
@Schema(description = "User Dto")  // для сваггера
public class UserDto {

    @Schema(description = "User id", example = "1")  // для сваггера
    @NotNull(message = "Id must be not null", groups = OnUpdate.class)
    private Long id;

    @Schema(description = "User name", example = "Alex")  // для сваггера
    @NotNull(message = "Name must be not nul",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Name must be small than 255 symbols",
            groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Schema(description = "User email", example = "al@ya.ru")  // для сваггера
    @NotNull(message = "Username must be not nul",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Username must be small than 255 symbols",
            groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @Schema(description = "User crypted password",
            example = "$2a$10$Xsdlghkdjfgisuaeofgdskfgjfd")
    // для сваггера
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null",
            groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @Schema(description = "User crypted password confimation",
            example = "$2a$10$Xsdlghkdjfgisuaeofgdskfgjfd")
    // для сваггера
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation must be not null",
            groups = {OnCreate.class, OnUpdate.class})
    private String passwordConfirmation;

}
