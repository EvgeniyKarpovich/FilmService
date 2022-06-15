package by.karpovich.filmSevice.service;

import by.karpovich.filmSevice.api.dto.ActorDto;
import by.karpovich.filmSevice.api.dto.ActorDtoFull;
import by.karpovich.filmSevice.api.dto.CountryDto;
import by.karpovich.filmSevice.api.dto.FilmDtoName;
import by.karpovich.filmSevice.exception.DuplicateException;
import by.karpovich.filmSevice.exception.NotFoundModelException;
import by.karpovich.filmSevice.jpa.model.ActorModel;
import by.karpovich.filmSevice.jpa.model.CountryModel;
import by.karpovich.filmSevice.jpa.model.FilmModel;
import by.karpovich.filmSevice.jpa.repository.ActorRepository;
import by.karpovich.filmSevice.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;

@Service
@Transactional
@Slf4j
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private CountryService countryService;
    @Autowired
    private FilmService filmService;

    public ActorDtoFull findById(Long id) {
        ActorModel actor = findByIdWhichWillReturnModel(id);

        ActorDtoFull dto = new ActorDtoFull();

        CountryModel countryModel = countryService.findByIdWhichWillReturnModel(actor.getPlaceOfBirth().getId());

        CountryDto countryDto = new CountryDto();
        countryDto.setId(countryModel.getId());
        countryDto.setName(countryModel.getName());

        Instant dateOfBirth = actor.getDateOfBirth();
        String dateOfBirthInString = DateUtil.mapFromInstantToString(dateOfBirth);

        List<FilmDtoName> filmModelList = new ArrayList<>();
        List<Long> filmIdByUser = actorRepository.getFilmsIdByActorId(actor.getId());

        if (filmIdByUser.size() > 0) {
            for (Long filmId : filmIdByUser) {
                FilmDtoName filmDto = new FilmDtoName();
                FilmModel filmModel = filmService.findByIdWhichWillReturnModel(filmId);
                filmDto.setId(filmModel.getId());
                filmDto.setName(filmModel.getName());

                filmModelList.add(filmDto);
            }
        }

        dto.setId(actor.getId());
        dto.setName(actor.getName());
        dto.setLastname(actor.getLastname());
        dto.setHeight(actor.getHeight());
        dto.setAwards(actor.getAwards());
        dto.setDateOfBirth(dateOfBirthInString);
        dto.setCountry(countryDto);
        dto.setFilms(filmModelList);

        return dto;
    }

    public void save(ActorDto dto) {
        validateAlreadyExists(null, dto);

        ActorModel actorModel = new ActorModel();

        CountryModel countryModel = countryService.findByIdWhichWillReturnModel(dto.getCountryId());

        Set<Long> filmsId = dto.getFilmsId();
        List<FilmModel> films = new ArrayList<>();
        for (Long filmId : filmsId) {
            FilmModel filmModel = filmService.findByIdWhichWillReturnModel(filmId);
            films.add(filmModel);
        }

        actorModel.setName(dto.getName());
        actorModel.setLastname(dto.getLastname());
        actorModel.setDateOfBirth(dto.getDateOfBirth());
        actorModel.setPlaceOfBirth(countryModel);
        actorModel.setHeight(dto.getHeight());
        actorModel.setAwards(dto.getAwards());
        actorModel.setFilms(films);

        actorRepository.save(actorModel);
        log.info("IN save -  Actor with name  '{}' saved", actorModel.getName());
    }

    public void update(ActorDto dto, Long id) {
        validateAlreadyExists(id, dto);

        ActorModel actorModel = new ActorModel();

        CountryModel countryModel = countryService.findByIdWhichWillReturnModel(dto.getCountryId());

        actorModel.setId(id);
        actorModel.setName(dto.getName());
        actorModel.setLastname(dto.getLastname());
        actorModel.setDateOfBirth(dto.getDateOfBirth());
        actorModel.setPlaceOfBirth(countryModel);
        actorModel.setHeight(dto.getHeight());
        actorModel.setAwards(dto.getAwards());

        Set<Long> filmsId = dto.getFilmsId();
        List<FilmModel> films = new ArrayList<>();
        for (Long filmId : filmsId) {
            FilmModel filmModel = filmService.findByIdWhichWillReturnModel(filmId);
            films.add(filmModel);
        }
        actorModel.setFilms(films);

        actorRepository.save(actorModel);
        log.info("IN update -  Actor  '{}' , updated", actorModel.getName());

    }

    public void deleteById(Long id) {
        if (actorRepository.findById(id).isPresent()) {
            actorRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(String.format("Actor with id = %s not found", id));
        }
        log.info("IN deleteById - Actor with id = {} deleted", id);
    }

    public List<ActorDtoFull> findAll() {
        List<ActorModel> actorList = actorRepository.findAll();
        List<Long> actorId = new ArrayList<>();
        List<ActorDtoFull> actorDtoFullList = new ArrayList<>();

        for (ActorModel actorModel : actorList) {
            actorId.add(actorModel.getId());
        }

        List<ActorModel> actorModelList = new ArrayList<>();

        for (Long id : actorId) {
            ActorModel actor = findByIdWhichWillReturnModel(id);

            actorModelList.add(actor);
        }

        ActorDtoFull dto;
        String dateOfBirthInString = "";

        for (ActorModel actorModel : actorModelList) {
            dto = new ActorDtoFull();
            CountryModel countryModel = countryService.findByIdWhichWillReturnModel(actorModel.getPlaceOfBirth().getId());
            CountryDto countryDto = new CountryDto();
            countryDto.setId(countryModel.getId());
            countryDto.setName(countryModel.getName());

            Instant dateOfBirth = actorModel.getDateOfBirth();
            dateOfBirthInString = DateUtil.mapFromInstantToString(dateOfBirth);

            List<FilmDtoName> filmModelList = new ArrayList<>();
            List<Long> filmIdByUser = actorRepository.getFilmsIdByActorId(actorModel.getId());

            if (filmIdByUser.size() > 0) {
                for (Long filmId : filmIdByUser) {
                    FilmDtoName filmDto = new FilmDtoName();
                    FilmModel filmModel = filmService.findByIdWhichWillReturnModel(filmId);
                    filmDto.setId(filmModel.getId());
                    filmDto.setName(filmModel.getName());

                    filmModelList.add(filmDto);
                }
            }

            dto.setId(actorModel.getId());
            dto.setName(actorModel.getName());
            dto.setLastname(actorModel.getLastname());
            dto.setHeight(actorModel.getHeight());
            dto.setAwards(actorModel.getAwards());
            dto.setDateOfBirth(dateOfBirthInString);
            dto.setCountry(countryDto);
            dto.setFilms(filmModelList);

            actorDtoFullList.add(dto);
        }
        log.info("IN findAll - the number of actors = {}", actorDtoFullList.size());

        return actorDtoFullList;
    }

    private void validateAlreadyExists(Long id, ActorDto dto) {
        Optional<ActorModel> check = actorRepository.findByNameAndLastname(dto.getName(), dto.getLastname());
        if (check.isPresent() && !Objects.equals(check.get().getId(), id)) {
            throw new DuplicateException(String.format("Actor with id = %s already exist", id));
        }
    }

    public ActorModel findByIdWhichWillReturnModel(Long id) {
        Optional<ActorModel> model = actorRepository.findById(id);
        ActorModel actorModel = model.orElseThrow(
                () -> new NotFoundModelException(String.format("Actor with id = %s not found", id)));
        log.info("IN findById -  Actor with id = {} found", actorModel.getId());
        return actorModel;
    }

}
