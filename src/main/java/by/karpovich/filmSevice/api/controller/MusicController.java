package by.karpovich.filmSevice.api.controller;

import by.karpovich.filmSevice.api.dto.MusicDto;
import by.karpovich.filmSevice.service.MusicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/musics")
@Api(tags = "Music Controller")
public class MusicController {

    @Autowired
    private MusicService musicService;

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
    public ResponseEntity<?> findAll() {
        List<MusicDto> all = musicService.findAll();

        if (all.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(all, HttpStatus.OK);
    }


    @ApiOperation(value = "Delete by id track")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        musicService.deleteById(id);

        return new ResponseEntity<>("Track deleted successfully", HttpStatus.OK);
    }

}
