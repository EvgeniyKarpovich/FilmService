package by.karpovich.filmSevice.api.dto;

import by.karpovich.filmSevice.api.validation.ValidCountry;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDto {

    @ApiModelProperty(value = "id", example = "1", position = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ApiModelProperty(value = "Name", example = "", required = true, position = 2)
    @NotBlank(message = "Enter Name")
    private String name;

    @ApiModelProperty(value = "LastName", example = "", required = true, position = 3)
    @NotBlank(message = "Enter Surname")
    private String lastname;

    @ApiModelProperty(value = "Date_of_birth", example = "2022-01-22T18:34:51.464+00:00", position = 4)
    @NotNull(message = "Enter Date")
    private Instant dateOfBirth;

    @ApiModelProperty(value = "country Id", example = "1", required = true, position = 5)
    @NotNull(message = "Enter Country")
    @ValidCountry
    private Long countryId;

    @ApiModelProperty(value = "Films", dataType = "List", example = "", position = 8)
    private List<Long> films = new ArrayList<>();

    //    private byte[] image;
}
