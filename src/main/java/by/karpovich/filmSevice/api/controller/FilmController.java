package by.karpovich.filmSevice.api.controller;

import by.karpovich.filmSevice.api.dto.searchCriteriaDto.FilmSearchCriteriaDto;
import by.karpovich.filmSevice.api.dto.FilmDto;
import by.karpovich.filmSevice.service.FilmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/films")
@Api(tags = "Film Controller")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @ApiOperation(value = "Find film by id")
    @GetMapping("/{id}")
    public FilmDto findById(@PathVariable("id") Long id) {
        return filmService.findById(id);
    }

    @ApiOperation(value = "Find all films by criteria")
    @GetMapping
    public List<FilmDto> findByCriteria(FilmSearchCriteriaDto filmSearchCriteriaDto) {
        return filmService.findAllByCriteria(filmSearchCriteriaDto);
    }

    @ApiOperation(value = "Save film")
    @PostMapping
    public FilmDto save(@RequestBody FilmDto filmDto) {
        return filmService.save(filmDto);
    }

    @ApiOperation(value = "Update Film by id")
    @PutMapping("/{id}")
    public FilmDto update(@RequestBody FilmDto filmDto,
                          @PathVariable("id") Long id) {
        return filmService.update(filmDto, id);
    }

    @ApiOperation(value = "Delete By id Film")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        filmService.deleteById(id);
    }

}
