package by.karpovich.filmSevice.service;

import by.karpovich.filmSevice.api.dto.ActorDto;
import by.karpovich.filmSevice.exception.DuplicateException;
import by.karpovich.filmSevice.exception.NotFoundModelException;
import by.karpovich.filmSevice.jpa.model.ActorModel;
import by.karpovich.filmSevice.jpa.repository.ActorRepository;
import by.karpovich.filmSevice.mapping.ActorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
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

    public ActorDto findById(Long id) {
        Optional<ActorModel> actorOptional = actorRepository.findById(id);
        ActorModel actor = actorOptional.orElseThrow(
                () -> new NotFoundModelException(String.format("Actor with id = %s not found", id)));
        log.info("IN findById -  Actor with id = {} found", actor.getId());
        return actorMapper.mapFromEntity(actor);
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

//    public List<ActorDto> findByCriteria(ActorSearchCriteriaDto actorSearchCriteriaDto) {
//        List<ActorModel> actorList = actorRepository.findAll(createFromCriteria(actorSearchCriteriaDto));
//        log.info("IN findAll - the number of actors according to these criteria = {}", actorList.size());
//        return actorMapper.mapFromEntity(actorList);
//    }

    private void validateAlreadyExists(Long id, ActorDto dto) {
        Optional<ActorModel> check = actorRepository.findByNameAndSurname(dto.getName(), dto.getLastName());
        if (check.isPresent() && !Objects.equals(check.get().getId(), id)) {
            throw new DuplicateException(String.format("Actor with id = %s already exist", id));
        }
    }

}
