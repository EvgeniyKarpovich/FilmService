package by.karpovich.filmSevice.service;

import by.karpovich.filmSevice.api.dto.FilmDto;
import by.karpovich.filmSevice.api.dto.searchCriteriaDto.FilmSearchCriteriaDto;
import by.karpovich.filmSevice.exception.DuplicateException;
import by.karpovich.filmSevice.exception.NotFoundModelException;
import by.karpovich.filmSevice.jpa.model.FilmModel;
import by.karpovich.filmSevice.jpa.repository.FilmRepository;
import by.karpovich.filmSevice.mapping.FilmMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static by.karpovich.filmSevice.jpa.specification.FilmSpecificationUtils.createFromCriteria;

@Service
@Transactional
@Slf4j
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private FilmMapper filmMapper;

    public FilmDto findById(Long id) {
        Optional<FilmModel> optionalFilm = filmRepository.findById(id);
        FilmModel film = optionalFilm.orElseThrow(
                () -> new NotFoundModelException(String.format("Film with id = %s not found", id)));
        log.info("IN findById -  Film with id = {} found", film.getId());
        return filmMapper.mapFromEntity(film);
    }

    public FilmDto save(FilmDto filmDto) {
        validateAlreadyExists(null, filmDto);
        FilmModel film = filmMapper.mapFromDto(filmDto);
        FilmModel savedFilm = filmRepository.save(film);
        log.info("IN save -  Film with name '{}' saved", filmDto.getName());
        return filmMapper.mapFromEntity(savedFilm);
    }

    public FilmDto update(FilmDto filmDto, Long id) {
        validateAlreadyExists(id, filmDto);
        FilmModel film = filmMapper.mapFromDto(filmDto);
        film.setId(id);
        FilmModel filmUpdated = filmRepository.save(film);
        log.info("IN update -  Film '{}' updated", filmDto.getName());
        return filmMapper.mapFromEntity(filmUpdated);
    }

    public List<FilmDto> findAllByCriteria(FilmSearchCriteriaDto filmSearchCriteriaDto) {
        List<FilmModel> filmList = filmRepository.findAll(createFromCriteria(filmSearchCriteriaDto));
        log.info("IN findAll - the number of actors according to these criteria = {}", filmList.size());
        return filmMapper.mapFromListEntity(filmList);
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

}
