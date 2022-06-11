package by.karpovich.filmSevice.service;

import by.karpovich.filmSevice.api.dto.ActorDtoFull;
import by.karpovich.filmSevice.api.dto.ActorForSaveDto;
import by.karpovich.filmSevice.api.dto.CountryDto;
import by.karpovich.filmSevice.api.dto.FilmDtoName;
import by.karpovich.filmSevice.exception.DuplicateException;
import by.karpovich.filmSevice.exception.NotFoundModelException;
import by.karpovich.filmSevice.jpa.model.ActorModel;
import by.karpovich.filmSevice.jpa.model.CountryModel;
import by.karpovich.filmSevice.jpa.model.FilmModel;
import by.karpovich.filmSevice.jpa.repository.ActorRepository;
import by.karpovich.filmSevice.jpa.repository.CountryRepository;
import by.karpovich.filmSevice.jpa.repository.FilmRepository;
import by.karpovich.filmSevice.mapping.ActorMapper;
import by.karpovich.filmSevice.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private ActorMapper actorMapper;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private FilmRepository filmRepository;

    public ActorDtoFull findActorById(Long id) {
        ActorModel actor = findActorModelById(id);

        ActorDtoFull dto = new ActorDtoFull();

        CountryModel countryModel = findCountryModelById(actor.getPlaceOfBirth().getId());

        CountryDto countryDto = new CountryDto();
        countryDto.setId(countryModel.getId());
        countryDto.setName(countryModel.getName());

        Instant dateOfBirth = actor.getDateOfBirth();
        String dateOfBirthInString = DateUtil.mapFromInstantToString(dateOfBirth);

        List<FilmDtoName> filmModelList = new ArrayList<>();
        List<Long> filmIdByUser = actorRepository.getFilmId(actor.getId());

        if (filmIdByUser.size() > 0) {
            for (Long filmId : filmIdByUser) {
                FilmDtoName filmDto = new FilmDtoName();
                FilmModel filmModel = findFilmModelById(filmId);
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

    public void save(ActorForSaveDto actorForSaveDto) {
        validateAlreadyExists(null, actorForSaveDto);

        ActorModel actorModel = new ActorModel();

        CountryModel countryModelById = findCountryModelById(actorForSaveDto.getCountryId());

        actorModel.setName(actorForSaveDto.getName());
        actorModel.setLastname(actorForSaveDto.getLastname());
        actorModel.setDateOfBirth(actorForSaveDto.getDateOfBirth());
        actorModel.setPlaceOfBirth(countryModelById);
        actorModel.setHeight(actorForSaveDto.getHeight());
        actorModel.setAwards(actorForSaveDto.getAwards());

        actorRepository.save(actorModel);
        log.info("IN save -  Actor with name  '{}' saved", actorModel.getName());
    }

    public void update(ActorForSaveDto actorForSaveDto, Long id) {
        validateAlreadyExists(id, actorForSaveDto);

        ActorModel actorModel = new ActorModel();

        CountryModel countryModelById = findCountryModelById(actorForSaveDto.getCountryId());

        actorModel.setId(id);
        actorModel.setName(actorForSaveDto.getName());
        actorModel.setLastname(actorForSaveDto.getLastname());
        actorModel.setDateOfBirth(actorForSaveDto.getDateOfBirth());
        actorModel.setPlaceOfBirth(countryModelById);
        actorModel.setHeight(actorForSaveDto.getHeight());
        actorModel.setAwards(actorForSaveDto.getAwards());

        actorRepository.save(actorModel);
        log.info("IN update -  Actor  '{}' , updated", actorModel.getName());

    }

    public void deleteById(Long id) {
        if (actorRepository.findById(id).isPresent()) {
            actorRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(String.format("Director with id = %s not found", id));
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
            ActorModel actor = findActorModelById(id);

            actorModelList.add(actor);
        }

        ActorDtoFull dto;
        String dateOfBirthInString = "";

        for (ActorModel actorModel : actorModelList) {
            dto = new ActorDtoFull();
            CountryModel countryModel = findCountryModelById(actorModel.getPlaceOfBirth().getId());
            CountryDto countryDto = new CountryDto();
            countryDto.setId(countryModel.getId());
            countryDto.setName(countryModel.getName());

            Instant dateOfBirth = actorModel.getDateOfBirth();
            dateOfBirthInString = DateUtil.mapFromInstantToString(dateOfBirth);

            List<FilmDtoName> filmModelList = new ArrayList<>();
            List<Long> filmIdByUser = actorRepository.getFilmId(actorModel.getId());

            if (filmIdByUser.size() > 0) {
                for (Long filmId : filmIdByUser) {
                    FilmDtoName filmDto = new FilmDtoName();
                    FilmModel filmModel = findFilmModelById(filmId);
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

    private void validateAlreadyExists(Long id, ActorForSaveDto dto) {
        Optional<ActorModel> check = actorRepository.findByNameAndLastname(dto.getName(), dto.getLastname());
        if (check.isPresent() && !Objects.equals(check.get().getId(), id)) {
            throw new DuplicateException(String.format("Actor with id = %s already exist", id));
        }
    }

    public ActorModel findActorModelById(Long id) {
        Optional<ActorModel> model = actorRepository.findById(id);
        ActorModel actorModel = model.orElseThrow(
                () -> new NotFoundModelException(String.format("Actor with id = %s not found", id)));
        log.info("IN findById -  Actor with id = {} found", actorModel.getId());
        return actorModel;
    }

    public CountryModel findCountryModelById(Long id) {
        Optional<CountryModel> byId = countryRepository.findById(id);
        CountryModel countryModel = byId.orElseThrow(
                () -> new NotFoundModelException(String.format("Country with id = %s not found", id)));

        return countryModel;
    }

    public FilmModel findFilmModelById(Long id) {
        Optional<FilmModel> filmById = filmRepository.findById(id);
        FilmModel filmModel = filmById.orElseThrow(
                () -> new NotFoundModelException(String.format("Film with id = %s not found", id)));

        return filmModel;
    }

}
