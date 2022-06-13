package by.karpovich.filmSevice.api.dto;

import by.karpovich.filmSevice.api.validation.ValidCountry;
import by.karpovich.filmSevice.api.validation.ValidDirector;
import by.karpovich.filmSevice.jpa.model.GenreForFilm;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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
public class FilmDto {

    @ApiModelProperty(value = "id", example = "1", position = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ApiModelProperty(value = "Name", example = "", required = true, position = 2)
    @NotBlank(message = "Enter Name")
    private String name;

    @ApiModelProperty(value = "releaseDate", example = "2022-01-22T18:34:51.464+00:00", position = 12)
    @NotNull(message = "Enter Date")
    private Instant releaseDate;

    @ApiModelProperty(value = "Genre", example = "DRAMA", required = true, position = 3)
    @NotNull(message = "Enter Genre")
    private GenreForFilm genre;

    @ApiModelProperty(value = "Director", example = "1", required = true, position = 4)
    @NotNull(message = "Enter director")
    @ValidDirector
    private Long directorId;

    @ApiModelProperty(value = "Duration in minutes", example = "154", required = true, position = 5)
    @NotNull(message = "Enter duration")
    @Min(value = 1, message = "the time must be correct")
    private Integer durationInMinutes;

    @ApiModelProperty(value = "Duration in minutes", example = "154", required = true, position = 6)
    @NotNull(message = "Enter duration")
    @Min(value = 1, message = "the time must be correct")
    private double ratingIMDB;

    @ApiModelProperty(value = "country Id", example = "1", required = true, position = 7)
    @NotNull(message = "Enter Country")
    @ValidCountry
    private Long countryId;

    @ApiModelProperty(value = "Name", example = "", position = 8)
    private String description;

    @ApiModelProperty(value = "Age", example = "", position = 9)
    private int ageLimit;

    @ApiModelProperty(value = "Actors", example = "", position = 10)
    private List<Long> actorsId = new ArrayList<>();

    @ApiModelProperty(value = "music", example = "", position = 11)
    private List<Long> musicsId = new ArrayList<>();

    //    private byte[] image;
}
