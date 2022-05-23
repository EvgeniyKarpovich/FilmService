package by.karpovich.filmSevice.api.controller;

import by.karpovich.filmSevice.api.dto.MusicDto;
import by.karpovich.filmSevice.service.MusicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public MusicDto findById(@PathVariable("id") Long id) {
        return musicService.findById(id);
    }

    @ApiOperation(value = "Save track")
    @PostMapping
    public MusicDto save(@RequestBody MusicDto musicDto) {
        return musicService.save(musicDto);
    }

    @ApiOperation(value = "Update track by id")
    @PutMapping("/{id}")
    public MusicDto update(@RequestBody MusicDto musicDto,
                           @PathVariable("id") Long id) {
        return musicService.update(musicDto, id);
    }

    @ApiOperation(value = "Find all tracks")
    @GetMapping
    public List<MusicDto> findAll() {
        return musicService.findAll();
    }


    @ApiOperation(value = "Delete by id track")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        musicService.deleteById(id);
    }

}
