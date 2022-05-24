package by.karpovich.filmSevice.mapping;

import by.karpovich.filmSevice.jpa.model.ActorModel;
import by.karpovich.filmSevice.jpa.model.FilmModel;
import by.karpovich.filmSevice.api.dto.ActorDto;
import by.karpovich.filmSevice.jpa.repository.FilmRepository;
import by.karpovich.filmSevice.service.CountryService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, uses = FilmMapper.class)
public interface ActorMapper {

    @Autowired
    FilmRepository repository = null;
    @Autowired
    CountryService service = null;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    @Mapping(source = "countryId", target = "placeOfBirth.id")
    ActorModel mapFromDto(ActorDto countryDto);

    @Mapping(source = "placeOfBirth.id", target = "countryId")
    ActorDto mapFromEntity(ActorModel country);

    List<ActorModel> mapFromListDto(List<ActorDto> countryDtoList);

    List<ActorDto> mapFromListEntity(List<ActorModel> countries);

    default Long fromFilm(FilmModel model) {
        return model == null ? null : model.getId();
    }

    default FilmModel fromLongToFilm(Long filmId) {
        return filmId == null ? null : repository.getById(filmId);
    }

//    @AfterMapping
//    protected void setCountry(@MappingTarget ActorModel model, ActorDto dto) {
//        CountryModel country = service.findByIdWhichWillReturnModel(dto.getCountryId());
//
//        model.setPlaceOfBirth(country);
//    }
//
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
