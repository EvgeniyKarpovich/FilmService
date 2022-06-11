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
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorForSaveDto {

    @ApiModelProperty(value = "Name", example = "", required = true, position = 1)
    @NotBlank(message = "Enter Name")
    private String name;
    @ApiModelProperty(value = "LastName", example = "", required = true, position = 2)
    @NotBlank(message = "Enter Surname")
    private String lastname;
    @ApiModelProperty(value = "Date_of_birth", example = "2022-01-22T18:34:51.464+00:00", position = 3)
    @NotNull(message = "Enter Date")
    private Instant dateOfBirth;
    @ApiModelProperty(value = "country Id", example = "1", required = true, position = 4)
    @NotNull(message = "Enter Country")
    @ValidCountry
    private Long countryId;
    @ApiModelProperty(value = "Height", example = "187", position = 5)
    @NotNull(message = "Enter height")
    @Min(value = 1, message = "height must be positive")
    private Integer height;
    @ApiModelProperty(value = "Awards", example = "", position = 6)
    private List<RewardForActor> awards = new ArrayList<>();

}
