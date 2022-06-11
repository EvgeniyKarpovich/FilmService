package by.karpovich.filmSevice.service;

import by.karpovich.filmSevice.api.dto.ActorDto;
import by.karpovich.filmSevice.api.dto.ActorDtoFull;
import by.karpovich.filmSevice.api.dto.CountryDto;
import by.karpovich.filmSevice.api.dto.FilmDtoName;
import by.karpovich.filmSevice.api.dto.searchCriteriaDto.ActorSearchCriteriaDto;
import by.karpovich.filmSevice.exception.DuplicateException;
import by.karpovich.filmSevice.exception.NotFoundModelException;
import by.karpovich.filmSevice.jpa.model.ActorModel;
import by.karpovich.filmSevice.jpa.model.CountryModel;
import by.karpovich.filmSevice.jpa.model.FilmModel;
import by.karpovich.filmSevice.jpa.repository.ActorRepository;
import by.karpovich.filmSevice.jpa.repository.CountryRepository;
import by.karpovich.filmSevice.jpa.repository.FilmRepository;
import by.karpovich.filmSevice.jpa.specification.ActorSpecificationUtils;
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

    public List<ActorModel> findByName(String name) {
        List<ActorModel> actorList = actorRepository.findByNameStartingWith(name);
        log.info("IN findByName -  the number of actors with this surname found : {}", actorList.size());
        return actorList;
    }

    public ActorDtoFull findById(Long id) {

        Optional<ActorModel> model = actorRepository.findById(id);
        ActorModel actor = model.orElseThrow(
                () -> new NotFoundModelException(String.format("Actor with id = %s not found", id)));
        log.info("IN findById -  Actor with id = {} found", actor.getId());

        ActorDtoFull dto = new ActorDtoFull();

        Optional<CountryModel> byId = countryRepository.findById(actor.getPlaceOfBirth().getId());
        CountryModel countryModel = byId.orElseThrow(
                () -> new NotFoundModelException(String.format("Country with id = %s not found", id)));
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
                Optional<FilmModel> filmById = filmRepository.findById(filmId);
                FilmModel filmModel = filmById.orElseThrow(
                        () -> new NotFoundModelException(String.format("Film with id = %s not found", filmId)));
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

    public ActorDto save(ActorDto actorDto) {
        validateAlreadyExists(null, actorDto);
        ActorModel actor = actorMapper.mapFromDto(actorDto);
        ActorModel actorSaved = actorRepository.save(actor);
        log.info("IN save -  Actor with name  '{}' saved", actorDto.getName());
        return actorMapper.mapFromEntity(actorSaved);
    }

    public ActorDto update(ActorDto actorDto, Long id) {
        validateAlreadyExists(id, actorDto);
        ActorModel actor = actorMapper.mapFromDto(actorDto);
        actor.setId(id);
        ActorModel actorUpdated = actorRepository.save(actor);
        log.info("IN update -  Film  '{}' , updated", actorDto.getName());
        return actorMapper.mapFromEntity(actorUpdated);
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
            Optional<ActorModel> model = actorRepository.findById(id);
            ActorModel actor = model.orElseThrow(
                    () -> new NotFoundModelException(String.format("Actor with id = %s not found", id)));
            log.info("IN findById -  Actor with id = {} found", actor.getId());
            actorModelList.add(actor);
        }

        ActorDtoFull dto ;
        String dateOfBirthInString = "";

        for (ActorModel actorModel : actorModelList) {
            dto = new ActorDtoFull();
            Optional<CountryModel> byId = countryRepository.findById(actorModel.getPlaceOfBirth().getId());
            CountryModel countryModel = byId.orElseThrow(
                    () -> new NotFoundModelException(String.format("Country with id = %s not found", actorModel.getId())));
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
                    Optional<FilmModel> filmById = filmRepository.findById(filmId);
                    FilmModel filmModel = filmById.orElseThrow(
                            () -> new NotFoundModelException(String.format("Film with id = %s not found", filmId)));
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

}
