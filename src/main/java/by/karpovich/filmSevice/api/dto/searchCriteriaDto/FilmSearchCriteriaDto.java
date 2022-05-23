package by.karpovich.filmSevice.api.dto.searchCriteriaDto;

import by.karpovich.filmSevice.jpa.model.GenreForFilm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmSearchCriteriaDto {

    private String name;

    private Instant date;

    private Instant startDate;

    private Instant endDate;

    private GenreForFilm genre;

    private Long countryId;

    private Long directorId;

    private Integer duration;
}
