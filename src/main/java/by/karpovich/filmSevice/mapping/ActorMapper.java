package by.karpovich.filmSevice.mapping;

import by.karpovich.filmSevice.api.dto.ActorDto;
import by.karpovich.filmSevice.jpa.model.ActorModel;
import by.karpovich.filmSevice.jpa.model.CountryModel;
import by.karpovich.filmSevice.jpa.model.FilmModel;
import by.karpovich.filmSevice.jpa.repository.FilmRepository;
import by.karpovich.filmSevice.service.CountryService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {FilmMapper.class, CountryMapper.class},
        imports = {Instant.class})
public abstract class ActorMapper {

    @Autowired
    private FilmRepository repository;
    @Autowired
    private CountryService service;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    public abstract ActorModel mapFromDto(ActorDto countryDto);

    public abstract ActorDto mapFromEntity(ActorModel country);

    public abstract List<ActorModel> mapFromListDto(List<ActorDto> countryDtoList);

    public abstract List<ActorDto> mapFromListEntity(List<ActorModel> countries);

    @AfterMapping
    public void mapFilms(ActorDto dto, @MappingTarget ActorModel model) {
        List<Long> filmsId = dto.getFilms();
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
