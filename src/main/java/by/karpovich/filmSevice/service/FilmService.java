package by.karpovich.filmSevice.service;

import by.karpovich.filmSevice.api.dto.FilmDto;
import by.karpovich.filmSevice.api.dto.searchCriteriaDto.FilmSearchCriteriaDto;
import by.karpovich.filmSevice.exception.DuplicateException;
import by.karpovich.filmSevice.exception.NotFoundModelException;
import by.karpovich.filmSevice.jpa.model.*;
import by.karpovich.filmSevice.jpa.repository.FilmRepository;
import by.karpovich.filmSevice.jpa.specification.FilmSpecificationUtils;
import by.karpovich.filmSevice.mapping.FilmMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@Slf4j
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private FilmMapper filmMapper;
    @Autowired
    private DirectorService directorService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private MusicService musicService;
    @Autowired
    private MailSender mailSender;

    public FilmDto findById(Long id) {

        FilmModel filmModel = findByIdWhichWillReturnModel(id);

        List<Long> actorsId = filmRepository.getActorsIdByFilmId(id);
        List<Long> musicsId = filmRepository.getMusicsIdByFilmId(id);

        FilmDto filmDto = new FilmDto();
        filmDto.setId(filmModel.getId());
        filmDto.setName(filmModel.getName());
        filmDto.setReleaseDate(filmModel.getReleaseDate());
        filmDto.setGenre(filmModel.getGenre());
        filmDto.setDirectorId(filmModel.getDirector().getId());
        filmDto.setDurationInMinutes(filmModel.getDurationInMinutes());
        filmDto.setRatingIMDB(filmModel.getRatingIMDB());
        filmDto.setCountryId(filmModel.getCountry().getId());
        filmDto.setAgeLimit(filmModel.getAgeLimit());
        filmDto.setDescription(filmModel.getDescription());
        filmDto.setActorsId(actorsId);
        filmDto.setMusicsId(musicsId);

        log.info("IN findById -  Film with id = {} found", filmModel.getId());
        return filmDto;
    }

    public void save(FilmDto filmDto) {
        validateAlreadyExists(null, filmDto);

        FilmModel filmModel = new FilmModel();

        DirectorModel directorModel = directorService.findByIdWhichWillReturnModel(filmDto.getDirectorId());
        CountryModel countryModel = countryService.findByIdWhichWillReturnModel(filmDto.getCountryId());

        List<Long> actorId = filmDto.getActorsId();
        List<ActorModel> actors = new ArrayList<>();
        for (Long actor : actorId) {
            ActorModel model = actorService.findByIdWhichWillReturnModel(actor);
            actors.add(model);
        }

        List<Long> musicsId = filmDto.getMusicsId();
        List<MusicModel> musicModels = new ArrayList<>();
        for (Long id : musicsId) {
            MusicModel model = musicService.findByIdWhichWillReturnModel(id);
            musicModels.add(model);
        }

        filmModel.setName(filmDto.getName());
        filmModel.setReleaseDate(filmDto.getReleaseDate());
        filmModel.setGenre(filmDto.getGenre());
        filmModel.setDirector(directorModel);
        filmModel.setDurationInMinutes(filmDto.getDurationInMinutes());
        filmModel.setRatingIMDB(filmDto.getRatingIMDB());
        filmModel.setCountry(countryModel);
        filmModel.setAgeLimit(filmDto.getAgeLimit());
        filmModel.setDescription(filmDto.getDescription());
        filmModel.setActors(actors);
        filmModel.setMusics(musicModels);

        filmRepository.save(filmModel);

        String message = String.format(
                "Hello Boss! \n" +
                        "We have added a new movie %s", filmDto.getName());
        mailSender.send("Phoenix-zzz@mail.ru", "Adding a movie", message);

        log.info("IN save -  Film with name '{}' saved", filmDto.getName());
    }

    public void update(FilmDto filmDto, Long id) {
        validateAlreadyExists(id, filmDto);

        FilmModel filmModel = new FilmModel();

        DirectorModel directorModel = directorService.findByIdWhichWillReturnModel(filmDto.getDirectorId());
        CountryModel countryModel = countryService.findByIdWhichWillReturnModel(filmDto.getCountryId());

        List<Long> actorId = filmDto.getActorsId();
        List<ActorModel> actors = new ArrayList<>();
        for (Long actor : actorId) {
            ActorModel model = actorService.findByIdWhichWillReturnModel(actor);
            actors.add(model);
        }

        List<Long> musicsId = filmDto.getMusicsId();
        List<MusicModel> musicModels = new ArrayList<>();
        for (Long trackId : musicsId) {
            MusicModel model = musicService.findByIdWhichWillReturnModel(trackId);
            musicModels.add(model);
        }

        filmModel.setId(id);
        filmModel.setName(filmDto.getName());
        filmModel.setReleaseDate(filmDto.getReleaseDate());
        filmModel.setGenre(filmDto.getGenre());
        filmModel.setDirector(directorModel);
        filmModel.setDurationInMinutes(filmDto.getDurationInMinutes());
        filmModel.setRatingIMDB(filmDto.getRatingIMDB());
        filmModel.setCountry(countryModel);
        filmModel.setAgeLimit(filmDto.getAgeLimit());
        filmModel.setDescription(filmDto.getDescription());
        filmModel.setActors(actors);
        filmModel.setMusics(musicModels);

        filmRepository.save(filmModel);

        log.info("IN update -  Film '{}' updated", filmDto.getName());
    }

    public List<FilmDto> findAllByCriteria(FilmSearchCriteriaDto filmSearchCriteriaDto) {
        List<FilmModel> filmList = filmRepository.findAll(FilmSpecificationUtils.createFromCriteria(filmSearchCriteriaDto));
        log.info("IN findAll - the number of actors according to these criteria = {}", filmList.size());
        return filmMapper.mapFromListEntity(filmList);
    }

    public Map<String, Object> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<FilmModel> all = filmRepository.findAll(pageable);
        List<FilmDto> filmDtoList = new ArrayList<>();

        for (FilmModel model : all) {

            FilmModel filmModel = findByIdWhichWillReturnModel(model.getId());

            List<Long> actorsId = filmRepository.getActorsIdByFilmId(model.getId());
            List<Long> musicsId = filmRepository.getMusicsIdByFilmId(model.getId());

            FilmDto filmDto = new FilmDto();
            filmDto.setId(filmModel.getId());
            filmDto.setName(filmModel.getName());
            filmDto.setReleaseDate(filmModel.getReleaseDate());
            filmDto.setGenre(filmModel.getGenre());
            filmDto.setDirectorId(filmModel.getDirector().getId());
            filmDto.setDurationInMinutes(filmModel.getDurationInMinutes());
            filmDto.setRatingIMDB(filmModel.getRatingIMDB());
            filmDto.setCountryId(filmModel.getCountry().getId());
            filmDto.setAgeLimit(filmModel.getAgeLimit());
            filmDto.setDescription(filmModel.getDescription());
            filmDto.setActorsId(actorsId);
            filmDto.setMusicsId(musicsId);

            filmDtoList.add(filmDto);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("tutorials", filmDtoList);
        response.put("currentPage", all.getNumber());
        response.put("totalItems", all.getTotalElements());
        response.put("totalPages", all.getTotalPages());

        return response;
    }

    public void deleteById(Long id) {
        if (filmRepository.findById(id).isPresent()) {
            filmRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format("Film with id = %s not found", id));
        }
        log.info("IN deleteById - Film with id = {} deleted", id);
    }

    private void validateAlreadyExists(Long id, FilmDto dto) {
        Optional<FilmModel> check = filmRepository.findByNameAndDirectorId(dto.getName(), dto.getDirectorId());
        if (check.isPresent() && !Objects.equals(check.get().getId(), id)) {
            throw new DuplicateException(String.format("Film with id = %s already exist", id));
        }
    }

    public FilmModel findByIdWhichWillReturnModel(Long id) {
        Optional<FilmModel> filmById = filmRepository.findById(id);
        FilmModel filmModel = filmById.orElseThrow(
                () -> new NotFoundModelException(String.format("Film with id = %s not found", id)));

        return filmModel;
    }

}
