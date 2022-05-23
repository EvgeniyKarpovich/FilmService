package by.karpovich.filmSevice.api.dto;

import by.karpovich.filmSevice.api.validation.ValidCountry;
import by.karpovich.filmSevice.jpa.model.RewardForActor;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorDto {

    @ApiModelProperty(value = "id", example = "1", position = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ApiModelProperty(value = "Name", example = "", required = true, position = 2)
    @NotBlank(message = "Enter Name")
    private String name;

    @ApiModelProperty(value = "LastName", example = "", required = true, position = 3)
    @NotBlank(message = "Enter Surname")
    private String lastName;

    @ApiModelProperty(value = "Date_of_birth", example = "2022-01-22T18:34:51.464+00:00", position = 4)
    @NotNull(message = "Enter Date")
    private Instant dateOfBirth;

    @ApiModelProperty(value = "country Id", example = "1", required = true, position = 5)
    @NotNull(message = "Enter Country")
    @ValidCountry
    private Long countryId;

    @ApiModelProperty(value = "Height", example = "187", position = 6)
    @NotNull(message = "Enter height")
    @Min(value = 1, message = "height must be positive")
    private Integer height;

    @ApiModelProperty(value = "Awards", dataType = "Set", example = "", position = 7)
    private Set<RewardForActor> awards = new HashSet<>();

    @ApiModelProperty(value = "Films", dataType = "List", example = "", position = 8)
    private List<Long> films = new ArrayList<>();

    //    private byte[] image;
}
