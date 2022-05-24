package by.karpovich.filmSevice.service;

import by.karpovich.filmSevice.exception.DuplicateException;
import by.karpovich.filmSevice.exception.NotFoundModelException;
import by.karpovich.filmSevice.mapping.UserMapper;
import by.karpovich.filmSevice.api.dto.UserDto;
import by.karpovich.filmSevice.jpa.model.UserModel;
import by.karpovich.filmSevice.jpa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public UserDto findById(Long id) {
        Optional<UserModel> userDto = userRepository.findById(id);
        UserModel userModel = userDto.orElseThrow(
                () -> new NotFoundModelException(String.format("User with id = %s not found", id)));
        log.info("IN findById -  User with id = {} found", userModel.getId());
        return userMapper.mapFromEntity(userModel);
    }

    public UserDto save(UserDto userDto) {
        validateAlreadyExists(null, userDto);
        UserModel userModel = userMapper.mapFromDto(userDto);
        UserModel user = userRepository.save(userModel);
        log.info("IN save -  User with name  '{}' saved", userDto.getName());
        return userMapper.mapFromEntity(user);
    }

    public UserDto update(UserDto userDto, Long id) {
        validateAlreadyExists(id, userDto);
        UserModel user = userMapper.mapFromDto(userDto);
        user.setId(id);
        UserModel userModel = userRepository.save(user);
        log.info("IN update -  User  '{}' , updated", userDto.getName());
        return userMapper.mapFromEntity(userModel);
    }

    public void deleteById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(String.format("User with id = %s not found", id));
        }
        log.info("IN deleteById - User with id = {} deleted", id);
    }

    public List<UserDto> findAll() {
        List<UserModel> userModels = userRepository.findAll();
        log.info("IN findAll - the number of actors according to these criteria = {}", userModels.size());
        return userMapper.mapFromListEntity(userModels);
    }

    private void validateAlreadyExists(Long id, UserDto dto) {
        Optional<UserModel> check = userRepository.findByLogin(dto.getLogin());
        if (check.isPresent() && !Objects.equals(check.get().getId(), id)) {
            throw new DuplicateException(String.format("User with id = %s already exist", id));
        }
    }
}
