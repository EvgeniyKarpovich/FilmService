package by.karpovich.filmSevice.mapping;

import by.karpovich.filmSevice.api.dto.DirectorDto;
import by.karpovich.filmSevice.jpa.model.CountryModel;
import by.karpovich.filmSevice.jpa.model.DirectorModel;
import by.karpovich.filmSevice.jpa.model.FilmModel;
import by.karpovich.filmSevice.jpa.repository.FilmRepository;
import by.karpovich.filmSevice.service.CountryService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {FilmMapper.class, CountryMapper.class})
public abstract class DirectorMapper {

    @Autowired
    private CountryService service;
    @Autowired
    private FilmRepository repository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    @Mapping(target = "films", ignore = true)
    @Mapping(source = "countryId", target = "placeOfBirth.id")
    public abstract DirectorModel mapFromDto(DirectorDto dto);

    @Mapping(source = "placeOfBirth.id", target = "countryId")
    @Mapping(target = "films", ignore = true)
    public abstract DirectorDto mapFromEntity(DirectorModel mode);

    public abstract List<DirectorModel> mapFromListDto(List<DirectorDto> dtoList);

    public abstract List<DirectorDto> mapFromListEntity(List<DirectorModel> modelList);

    @AfterMapping
    protected void mapFilms(DirectorDto dto, @MappingTarget DirectorModel model) {
        List<Long> filmsId = dto.getFilms();
        List<FilmModel> films = new ArrayList<>();
        for (Long id : filmsId) {
            FilmModel filmModel = repository.getOne(id);
            films.add(filmModel);
        }
        model.setFilms(films);
    }

    @AfterMapping
    protected void setCountry(@MappingTarget DirectorModel model, DirectorDto dto) {
        CountryModel country = service.findByIdWhichWillReturnModel(dto.getCountryId());

        model.setPlaceOfBirth(country);
    }

}
