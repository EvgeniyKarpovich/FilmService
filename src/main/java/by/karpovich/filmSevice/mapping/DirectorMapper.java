package by.karpovich.filmSevice.mapping;

import by.karpovich.filmSevice.api.dto.DirectorDto;
import by.karpovich.filmSevice.jpa.model.DirectorModel;
import by.karpovich.filmSevice.jpa.model.FilmModel;
import by.karpovich.filmSevice.jpa.repository.FilmRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {FilmMapper.class, FilmRepository.class})
public interface DirectorMapper {

    @Autowired
    FilmRepository repository = null;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    @Mapping(source = "countryId", target = "placeOfBirth.id")
    DirectorModel mapFromDto(DirectorDto countryDto);

    @Mapping(source = "placeOfBirth.id", target = "countryId")
    DirectorDto mapFromEntity(DirectorModel country);

    List<DirectorModel> mapFromListDto(List<DirectorDto> countryDtoList);

    List<DirectorDto> mapFromListEntity(List<DirectorModel> countries);

    default Long fromFilm(FilmModel model) {
        return model == null ? null : model.getId();
    }

    default FilmModel fromLongToFilm(Long filmId) {
        return filmId == null ? null : repository.getById(filmId);
    }

//    @AfterMapping
//    default void mapFilms(DirectorDto dto, @MappingTarget DirectorModel model) {
//        List<Long> filmsId = dto.getFilms();
//        List<FilmModel> films = new ArrayList<>();
//        for (Long id : filmsId) {
//            FilmModel filmModel = repository.getById(id);
//            films.add(filmModel);
//        }
//        model.setFilms(films);
//    }

//    @AfterMapping
//    default void setCountry(@MappingTarget DirectorModel model, DirectorDto dto) {
//        CountryModel country = service.findByIdWhichWillReturnModel(dto.getCountryId());
//
//        model.setPlaceOfBirth(country);
//    }
}
