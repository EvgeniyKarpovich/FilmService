package by.karpovich.filmSevice.api.controller;

import by.karpovich.filmSevice.api.dto.MusicDto;
import by.karpovich.filmSevice.jpa.model.MusicModel;
import by.karpovich.filmSevice.jpa.repository.MusicRepository;
import by.karpovich.filmSevice.mapping.MusicMapper;
import by.karpovich.filmSevice.service.MusicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/musics")
@Api(tags = "Music Controller")
public class MusicController {

    @Autowired
    private MusicService musicService;
    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private MusicMapper musicMapper;

    @ApiOperation(value = "Find by id track")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        MusicDto byId = musicService.findById(id);

        if (byId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @ApiOperation(value = "Save track")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody MusicDto musicDto) {
        MusicDto save = musicService.save(musicDto);

        if (save == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Track saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update track by id")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody MusicDto musicDto,
                                    @PathVariable("id") Long id) {
        MusicDto update = musicService.update(musicDto, id);

        if (update == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Track saved successfully", HttpStatus.OK);
    }


    @ApiOperation(value = "Find all tracks")
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "2") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
            Page<MusicModel> musicModels = musicRepository.findAll(pageable);
            List<MusicModel> content = musicModels.getContent();

            List<MusicDto> countryDtoList = musicMapper.mapFromListEntity(content);

            Map<String, Object> response = new HashMap<>();
            response.put("tutorials", countryDtoList);
            response.put("currentPage", musicModels.getNumber());
            response.put("totalItems", musicModels.getTotalElements());
            response.put("totalPages", musicModels.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Delete by id track")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        musicService.deleteById(id);

        return new ResponseEntity<>("Track deleted successfully", HttpStatus.OK);
    }

}
