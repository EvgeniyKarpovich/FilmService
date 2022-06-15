package by.karpovich.filmSevice.service;

import by.karpovich.filmSevice.exception.DuplicateException;
import by.karpovich.filmSevice.exception.NotFoundModelException;
import by.karpovich.filmSevice.jpa.model.MusicModel;
import by.karpovich.filmSevice.mapping.MusicMapper;
import by.karpovich.filmSevice.api.dto.MusicDto;
import by.karpovich.filmSevice.jpa.repository.MusicRepository;
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
public class MusicService {

    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private MusicMapper musicMapper;

    public MusicDto findById(Long id) {
        Optional<MusicModel> optionalMusic = musicRepository.findById(id);
        MusicModel music = optionalMusic.orElseThrow(
                () -> new NotFoundModelException(String.format("Track with id = %s not found", id)));
        log.info("IN findById -  Track with id = {} found", music.getId());
        return musicMapper.mapFromEntity(music);
    }

    public Map<String, Object> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<MusicModel> musicModels = musicRepository.findAll(pageable);
        List<MusicModel> content = musicModels.getContent();
        List<MusicDto> musicDtoList = musicMapper.mapFromListEntity(content);

        Map<String, Object> response = new HashMap<>();

        response.put("tutorials", musicDtoList);
        response.put("currentPage", musicModels.getNumber());
        response.put("totalItems", musicModels.getTotalElements());
        response.put("totalPages", musicModels.getTotalPages());

        log.info("IN findAll - the number tracks = {}", musicDtoList.size());

        return response;
    }

    public MusicDto save(MusicDto musicDto) {
        validateAlreadyExists(null, musicDto);
        MusicModel music = musicMapper.mapFromDto(musicDto);
        MusicModel musicSaved = musicRepository.save(music);
        log.info("IN save -  Track with name '{}' saved", musicDto.getName());
        return musicMapper.mapFromEntity(musicSaved);
    }

    public MusicDto update(MusicDto musicDto, Long id) {
        validateAlreadyExists(id, musicDto);
        MusicModel music = musicMapper.mapFromDto(musicDto);
        music.setId(id);
        MusicModel musicUpdated = musicRepository.save(music);
        log.info("IN update -  Track with name '{}' updated", musicDto.getName());
        return musicMapper.mapFromEntity(musicUpdated);
    }

    public void deleteById(Long id) {
        if (musicRepository.findById(id).isPresent()) {
            musicRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format("Track with id = %s not found", id));
        }
        log.info("IN deleteById - Track with id =  {} deleted", id);
    }

    private void validateAlreadyExists(Long id, MusicDto dto) {
        Optional<MusicModel> checkMusic = musicRepository.findByNameAndAuthor(dto.getName(), dto.getAuthor());
        if (checkMusic.isPresent() && Objects.equals(dto.getId(), id)) {
            throw new DuplicateException(String.format("Track with id = %s already exist", id));
        }
    }
    public MusicModel findByIdWhichWillReturnModel(Long id) {
        Optional<MusicModel> model = musicRepository.findById(id);
        MusicModel musicModel = model.orElseThrow(
                () -> new NotFoundModelException(String.format("Music with id = %s not found", id)));
        log.info("IN findById -  Music with id = {} found", musicModel.getId());
        return musicModel;
    }


}
