package by.karpovich.filmSevice.mapping;

import by.karpovich.filmSevice.api.dto.FilmDto;
import by.karpovich.filmSevice.jpa.model.*;
import by.karpovich.filmSevice.jpa.repository.ActorRepository;
import by.karpovich.filmSevice.jpa.repository.MusicRepository;
import by.karpovich.filmSevice.service.CountryService;
import by.karpovich.filmSevice.service.DirectorService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {ActorMapper.class, MusicMapper.class,
                DirectorMapper.class, CountryMapper.class})
public abstract class FilmMapper {

    @Autowired
    private DirectorService directorService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MusicRepository musicRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "musics", ignore = true)
    @Mapping(source = "directorId", target = "director.id")
    @Mapping(source = "countryId", target = "country.id")
    public abstract FilmModel mapFromDto(FilmDto countryDto);

    @Mapping(source = "director.id", target = "directorId")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "musics", ignore = true)
    public abstract FilmDto mapFromEntity(FilmModel country);

    public abstract List<FilmModel> mapFromListDto(List<FilmDto> countryDtoList);

    public abstract List<FilmDto> mapFromListEntity(List<FilmModel> countries);

    @AfterMapping
    protected void setCountry(@MappingTarget FilmModel model, FilmDto dto) {
        CountryModel country = countryService.findByIdWhichWillReturnModel(dto.getCountryId());

        model.setCountry(country);
    }

    @AfterMapping
    protected void setDirector(@MappingTarget FilmModel model, FilmDto dto) {
        DirectorModel director = directorService.findByIdWhichWillReturnModel(dto.getDirectorId());

        model.setDirector(director);
    }

    @AfterMapping
    protected void mapActor(FilmDto dto, @MappingTarget FilmModel model) {
        List<Long> actorId = dto.getActors();
        List<ActorModel> films = new ArrayList<>();
        for (Long id : actorId) {
            ActorModel actor = actorRepository.getById(id);
            films.add(actor);
        }
        model.setActors(films);
    }

    @AfterMapping
    protected void mapMusic(FilmDto dto, @MappingTarget FilmModel model) {
        List<Long> musicId = dto.getMusics();
        List<MusicModel> musics = new ArrayList<>();
        for (Long id : musicId) {
            MusicModel music = musicRepository.getById(id);
            musics.add(music);
        }
        model.setMusics(musics);
    }

//    default Long fromMusic(MusicModel model) {
//        return model == null ? null : model.getId();
//    }
//
//    default MusicModel fromLongToMusic(Long musicId) {
//        return musicId == null ? null : musicRepository.getById(musicId);
//    }
//
//    default Long fromActor(ActorModel model) {
//        return model == null ? null : model.getId();
//    }
//
//    default ActorModel fromLongToActor(Long actorId) {
//        return actorId == null ? null : actorRepository.getById(actorId);
//    }

}
