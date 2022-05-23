package by.karpovich.filmSevice.mapping;

import by.karpovich.filmSevice.api.dto.FilmDto;
import by.karpovich.filmSevice.jpa.model.ActorModel;
import by.karpovich.filmSevice.jpa.model.CountryModel;
import by.karpovich.filmSevice.jpa.model.FilmModel;
import by.karpovich.filmSevice.jpa.model.MusicModel;
import by.karpovich.filmSevice.jpa.repository.ActorRepository;
import by.karpovich.filmSevice.jpa.repository.MusicRepository;
import by.karpovich.filmSevice.service.CountryService;
import by.karpovich.filmSevice.service.DirectorService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {ActorMapper.class, CountryMapper.class,
                DirectorMapper.class, MusicMapper.class},
        imports = {Instant.class})
public abstract class FilmMapper {

    @Autowired
    private CountryService service;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    DirectorService directorService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    public abstract FilmModel mapFromDto(FilmDto countryDto);

    public abstract FilmDto mapFromEntity(FilmModel country);

    public abstract List<FilmModel> mapFromListDto(List<FilmDto> countryDtoList);

    public abstract List<FilmDto> mapFromListEntity(List<FilmModel> countries);

    @AfterMapping
    public void mapActor(FilmDto dto, @MappingTarget FilmModel model) {
        List<Long> actorId = dto.getActors();
        List<ActorModel> films = new ArrayList<>();
        for (Long id : actorId) {
            ActorModel actor = actorRepository.getOne(id);
            films.add(actor);
        }
        model.setActors(films);
    }

    @AfterMapping
    public void mapMusic(FilmDto dto, @MappingTarget FilmModel model) {
        List<Long> musicId = dto.getActors();
        List<MusicModel> musics = new ArrayList<>();
        for (Long id : musicId) {
            MusicModel music = musicRepository.getOne(id);
            musics.add(music);
        }
        model.setMusics(musics);
    }

    @AfterMapping
    protected void setCountry(@MappingTarget FilmModel model, FilmDto dto) {
        CountryModel country = service.findByIdWhichWillReturnModel(dto.getCountryId());

        model.setCountry(country);
    }
}
