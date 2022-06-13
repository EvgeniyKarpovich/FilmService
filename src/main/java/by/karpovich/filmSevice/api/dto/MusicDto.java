package by.karpovich.filmSevice.api.dto;

import by.karpovich.filmSevice.jpa.model.GenreMusic;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MusicDto {

    @ApiModelProperty(value = "id", example = "1", position = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ApiModelProperty(value = "Name", example = "", required = true, position = 2)
    @NotBlank(message = "Enter Name")
    private String name;

    @ApiModelProperty(value = "Author", example = "", required = true, position = 3)
    @NotBlank(message = "Enter author")
    private String author;

    @ApiModelProperty(value = "Genre", example = "POP", required = true, position = 4)
    @NotNull(message = "Enter Genre")
    private GenreMusic genre;

    @ApiModelProperty(value = "Duration in minutes", example = "3", required = true, position = 5)
    @NotNull(message = "Enter duration")
    @Min(value = 1, message = "the time must be correct")
    private double durationInMinutes;

}
