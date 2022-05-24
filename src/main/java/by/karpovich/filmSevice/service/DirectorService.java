package by.karpovich.filmSevice.service;

import by.karpovich.filmSevice.api.dto.DirectorDto;
import by.karpovich.filmSevice.exception.DuplicateException;
import by.karpovich.filmSevice.exception.NotFoundModelException;
import by.karpovich.filmSevice.jpa.model.DirectorModel;
import by.karpovich.filmSevice.mapping.DirectorMapper;
import by.karpovich.filmSevice.jpa.repository.DirectorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@Service
@Slf4j
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private DirectorMapper directorMapper;

    public DirectorDto findById(Long id) {
        Optional<DirectorModel> optionalDirector = directorRepository.findById(id);
        DirectorModel director = optionalDirector.orElseThrow(
                () -> new NotFoundModelException(String.format("Director with id = %s not found", id)));
        log.info("IN findById -  Director with id = {} found", director.getId());
        return directorMapper.mapFromEntity(director);
    }

    public List<DirectorDto> findAll() {
        List<DirectorModel> directorsList = directorRepository.findAll();
        log.info("IN findAll - the number directors = {}", directorsList.size());
        return directorMapper.mapFromListEntity(directorsList);
    }

    public DirectorDto save(DirectorDto directorDto) {
        validateAlreadyExists(null, directorDto);
        DirectorModel director = directorMapper.mapFromDto(directorDto);
        DirectorModel directorSaved = directorRepository.save(director);
        log.info("IN save -  Director with name '{}' saved", directorDto.getName());
        return directorMapper.mapFromEntity(directorSaved);
    }

    public DirectorDto update(DirectorDto directorDto, Long id) {
        validateAlreadyExists(id, directorDto);
        DirectorModel director = directorMapper.mapFromDto(directorDto);
        director.setId(id);
        DirectorModel directorUpdated = directorRepository.save(director);
        log.info("IN update -  Director with name '{}' updated", directorDto.getName());
        return directorMapper.mapFromEntity(directorUpdated);
    }

    public void deleteById(Long id) {
        if (directorRepository.findById(id).isPresent()) {
            directorRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(String.format("Director with id = %s not found", id));
        }
        log.info("IN deleteById - Director with id = {} deleted", id);
    }

    private void validateAlreadyExists(Long id, DirectorDto dto) {
        Optional<DirectorModel> check = directorRepository.findByNameAndLastname(dto.getName(), dto.getLastname());
        if (check.isPresent() && !Objects.equals(check.get().getId(), id)) {
            throw new DuplicateException(String.format("Director with id = %s and Name '%s' already exist", id, dto.getName()));
        }
    }

    public DirectorModel findByIdWhichWillReturnModel(Long id) {
        Optional<DirectorModel> optionalDirector = directorRepository.findById(id);
        return optionalDirector.orElseThrow(
                () -> new EntityNotFoundException("Director with ID = " + id + " not found"));
    }

}
