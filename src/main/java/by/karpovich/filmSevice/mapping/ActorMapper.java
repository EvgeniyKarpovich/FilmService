package by.karpovich.filmSevice.mapping;

import by.karpovich.filmSevice.api.dto.ActorDto;
import by.karpovich.filmSevice.jpa.model.ActorModel;
import by.karpovich.filmSevice.jpa.model.CountryModel;
import by.karpovich.filmSevice.jpa.model.FilmModel;
import by.karpovich.filmSevice.jpa.repository.FilmRepository;
import by.karpovich.filmSevice.service.CountryService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {FilmMapper.class, CountryMapper.class})
public abstract class ActorMapper {

    @Autowired
    private CountryService service;
    @Autowired
    private FilmRepository repository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    @Mapping(target = "films", ignore = true)
    @Mapping(source = "countryId", target = "placeOfBirth.id")
    public abstract ActorModel mapFromDto(ActorDto dto);

    @Mapping(source = "placeOfBirth.id", target = "countryId")
    @Mapping(target = "filmsId", ignore = true)
    public abstract ActorDto mapFromEntity(ActorModel model);

    public abstract List<ActorModel> mapFromListDto(List<ActorDto> dtoList);

    public abstract List<ActorDto> mapFromListEntity(List<ActorModel> modelList);

    @AfterMapping
    public void mapFilms(ActorDto dto, @MappingTarget ActorModel model) {
        List<Long> filmsId = dto.getFilmsId();
        List<FilmModel> films = new ArrayList<>();
        for (Long id : filmsId) {
            FilmModel hall = repository.getOne(id);
            films.add(hall);
        }
        model.setFilms(films);
    }

    @AfterMapping
    protected void setCountry(@MappingTarget ActorModel model, ActorDto dto) {
        CountryModel country = service.findByIdWhichWillReturnModel(dto.getCountryId());

        model.setPlaceOfBirth(country);
    }

}
