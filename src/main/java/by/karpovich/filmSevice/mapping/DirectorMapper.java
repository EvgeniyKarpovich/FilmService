package by.karpovich.filmSevice.mapping;

import by.karpovich.filmSevice.api.dto.DirectorDto;
import by.karpovich.filmSevice.jpa.model.CountryModel;
import by.karpovich.filmSevice.jpa.model.DirectorModel;
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
public abstract class DirectorMapper {

    @Autowired
    private FilmRepository repository;
    @Autowired
    private CountryService service;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    public abstract DirectorModel mapFromDto(DirectorDto countryDto);

    public abstract DirectorDto mapFromEntity(DirectorModel country);

    public abstract List<DirectorModel> mapFromListDto(List<DirectorDto> countryDtoList);

    public abstract List<DirectorDto> mapFromListEntity(List<DirectorModel> countries);

    @AfterMapping
    public void mapFilms(DirectorDto dto, @MappingTarget DirectorModel model) {
        List<Long> filmsId = dto.getFilms();
        List<FilmModel> films = new ArrayList<>();
        for (Long id : filmsId) {
            FilmModel hall = repository.getOne(id);
            films.add(hall);
        }
        model.setFilms(films);
    }

    @AfterMapping
    protected void setCountry(@MappingTarget DirectorModel model, DirectorDto dto) {
        CountryModel country = service.findByIdWhichWillReturnModel(dto.getCountryId());

        model.setPlaceOfBirth(country);
    }
}
