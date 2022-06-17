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
import by.karpovich.filmSevice.jpa.repository.FilmRepository;
import by.karpovich.filmSevice.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private FilmRepository filmRepository;

    public ActorDto findById(Long id) {
        ActorModel model = findByIdWhichWillReturnModel(id);

        ActorDto dto = new ActorDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setLastname(model.getLastname());
        dto.setDateOfBirth(model.getDateOfBirth());
        dto.setCountryId(model.getPlaceOfBirth().getId());
        dto.setHeight(model.getHeight());
        dto.setAwards(model.getAwards());

        List<Long> filmIdByActor = actorRepository.getFilmsIdByActorId(model.getId());
        dto.setFilmsId(filmIdByActor);

        return dto;
    }

    public void save(ActorDto dto) {
        validateAlreadyExists(null, dto);

        ActorModel actorModel = new ActorModel();

        CountryModel countryModel = countryService.findByIdWhichWillReturnModel(dto.getCountryId());

        List<Long> filmsId = dto.getFilmsId();
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

        List<Long> filmsId = dto.getFilmsId();
        List<FilmModel> films = new ArrayList<>();
        for (Long filmId : filmsId) {
            FilmModel filmModel = filmService.findByIdWhichWillReturnModel(filmId);
            films.add(filmModel);
        }

        actorModel.setId(id);
        actorModel.setName(dto.getName());
        actorModel.setLastname(dto.getLastname());
        actorModel.setDateOfBirth(dto.getDateOfBirth());
        actorModel.setPlaceOfBirth(countryModel);
        actorModel.setHeight(dto.getHeight());
        actorModel.setAwards(dto.getAwards());
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

    public Map<String, Object> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<ActorModel> modelList = actorRepository.findAll(pageable);

        List<ActorDto> result = new ArrayList<>();
        for (ActorModel model : modelList) {
            ActorDto dto = new ActorDto();
            dto.setId(model.getId());
            dto.setName(model.getName());
            dto.setLastname(model.getLastname());
            dto.setDateOfBirth(model.getDateOfBirth());
            dto.setCountryId(model.getPlaceOfBirth().getId());
            dto.setHeight(model.getHeight());
            dto.setAwards(model.getAwards());

            List<Long> filmIdByActor = actorRepository.getFilmsIdByActorId(model.getId());
            dto.setFilmsId(filmIdByActor);

            result.add(dto);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("tutorials", result);
        response.put("currentPage", modelList.getNumber());
        response.put("totalItems", modelList.getTotalElements());
        response.put("totalPages", modelList.getTotalPages());

        log.info("IN findAll - the number of actors = {}", result.size());
        return response;
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
