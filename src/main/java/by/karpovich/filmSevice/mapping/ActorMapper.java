package by.karpovich.filmSevice.mapping;

import by.karpovich.filmSevice.api.dto.ActorDto;
import by.karpovich.filmSevice.jpa.model.ActorModel;
import by.karpovich.filmSevice.jpa.model.CountryModel;
import by.karpovich.filmSevice.service.CountryService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {FilmMapper.class, CountryMapper.class})
public abstract class ActorMapper {

    @Autowired
    private CountryService service;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    @Mapping(target = "films", ignore = true)
    @Mapping(source = "countryId", target = "placeOfBirth.id")
    public abstract ActorModel mapFromDto(ActorDto countryDto);

    @Mapping(source = "placeOfBirth.id", target = "countryId")
    public abstract ActorDto mapFromEntity(ActorModel country);

    public abstract List<ActorModel> mapFromListDto(List<ActorDto> countryDtoList);

    public abstract List<ActorDto> mapFromListEntity(List<ActorModel> countries);

    @AfterMapping
    protected void setCountry(@MappingTarget ActorModel model, ActorDto dto) {
        CountryModel country = service.findByIdWhichWillReturnModel(dto.getCountryId());

        model.setPlaceOfBirth(country);
    }

//    default Long fromFilm(FilmModel model) {
//        return model == null ? null : model.getId();
//    }
//
//    default FilmModel fromLongToFilm(Long filmId) {
//        return filmId == null ? null : repository.getById(filmId);
//    }

//    @AfterMapping
//    public void mapFilms(ActorDto dto, @MappingTarget ActorModel model) {
//        List<Long> filmsId = dto.getFilms();
//        List<FilmModel> films = new ArrayList<>();
//        for (Long id : filmsId) {
//            FilmModel hall = repository.getById(id);
//            films.add(hall);
//        }
//        model.setFilms(films);
//    }

}
