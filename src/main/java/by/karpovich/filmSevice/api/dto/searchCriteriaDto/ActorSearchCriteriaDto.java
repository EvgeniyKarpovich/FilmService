package by.karpovich.filmSevice.api.dto.searchCriteriaDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorSearchCriteriaDto {

    private String name;

    private Instant date;

    private String surname;

    private Instant startDate;

    private Instant endDate;

    private Long countryId;

}
