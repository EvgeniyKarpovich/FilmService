package by.karpovich.filmSevice.mapping;

import by.karpovich.filmSevice.jpa.model.ActorModel;
import by.karpovich.filmSevice.jpa.model.FilmModel;
import by.karpovich.filmSevice.jpa.model.MusicModel;
import by.karpovich.filmSevice.api.dto.FilmDto;
import by.karpovich.filmSevice.jpa.repository.ActorRepository;
import by.karpovich.filmSevice.jpa.repository.MusicRepository;
import by.karpovich.filmSevice.service.CountryService;
import by.karpovich.filmSevice.service.DirectorService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {ActorMapper.class, MusicMapper.class})
public interface FilmMapper {

    @Autowired
    CountryService service = null;
    @Autowired
    ActorRepository actorRepository = null;
    @Autowired
    MusicRepository musicRepository = null;
    @Autowired
    DirectorService directorService = null;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    @Mapping(source = "directorId", target = "director.id")
    @Mapping(source = "countryId", target = "country.id")
    FilmModel mapFromDto(FilmDto countryDto);

    @Mapping(source = "director.id", target = "directorId")
    @Mapping(source = "country.id", target = "countryId")
    FilmDto mapFromEntity(FilmModel country);

    List<FilmModel> mapFromListDto(List<FilmDto> countryDtoList);

    List<FilmDto> mapFromListEntity(List<FilmModel> countries);

    default Long fromMusic(MusicModel model) {
        return model == null ? null : model.getId();
    }

    default MusicModel fromLongToMusic(Long musicId) {
        return musicId == null ? null : musicRepository.getById(musicId);
    }

    default Long fromActor(ActorModel model) {
        return model == null ? null : model.getId();
    }

    default ActorModel fromLongToActor(Long actorId) {
        return actorId == null ? null : actorRepository.getById(actorId);
    }

//    @AfterMapping
//    protected void mapActor(FilmDto dto, @MappingTarget FilmModel model) {
//        List<Long> actorId = dto.getActors();
//        List<ActorModel> films = new ArrayList<>();
//        for (Long id : actorId) {
//            ActorModel actor = actorRepository.getById(id);
//            films.add(actor);
//        }
//        model.setActors(films);
//    }

//    @AfterMapping
//    protected void mapMusic(FilmDto dto, @MappingTarget FilmModel model) {
//        List<Long> musicId = dto.getMusics();
//        List<MusicModel> musics = new ArrayList<>();
//        for (Long id : musicId) {
//            MusicModel music = musicRepository.getById(id);
//            musics.add(music);
//        }
//        model.setMusics(musics);
//    }
//
//    @AfterMapping
//    protected void setCountry(@MappingTarget FilmModel model, FilmDto dto) {
//        CountryModel country = service.findByIdWhichWillReturnModel(dto.getCountryId());
//
//        model.setCountry(country);
//    }
//
//    @AfterMapping
//    protected void setDirector(@MappingTarget FilmModel model, FilmDto dto) {
//        DirectorModel director = directorService.findByIdWhichWillReturnModel(dto.getDirectorId());
//
//        model.setDirector(director);
//    }
}
