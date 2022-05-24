package by.karpovich.filmSevice.api.dto;

import by.karpovich.filmSevice.jpa.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @ApiModelProperty(value = "id", example = "1", position = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ApiModelProperty(value = "Name", example = "", required = true, position = 2)
    @NotBlank(message = "Enter Name")
    private String name;

    @ApiModelProperty(value = "LastName", example = "", required = true, position = 3)
    @NotBlank(message = "Enter Surname")
    private String lastName;

    @ApiModelProperty(value = "Login", example = "", required = true, position = 4)
    @NotBlank(message = "Enter login")
    private String login;

    @ApiModelProperty(value = "email", example = "", required = true, position = 5)
    @NotBlank(message = "Enter email")
    private String email;

    @ApiModelProperty(value = "password", example = "", required = true, position = 6)
    @NotBlank(message = "Enter password")
    private String password;

    @ApiModelProperty(value = "Date_of_birth", example = "2022-01-22T18:34:51.464+00:00", position = 7)
    @NotNull(message = "Enter Date")
    private Instant dateOfBirth;

    @ApiModelProperty(value = "Role", example = "", required = true, position = 8)
    @NotNull(message = "Enter role")
    private Role role;

}
