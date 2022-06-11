package by.karpovich.filmSevice.api.dto;

import by.karpovich.filmSevice.jpa.model.RewardForActor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorDtoFull {

    private Long id;
    private String name;
    private String lastname;
    private String dateOfBirth;
    private CountryDto country;
    private Integer height;
    private List<RewardForActor> awards = new ArrayList<>();
    private List<FilmDtoName> films = new ArrayList<>();

}
