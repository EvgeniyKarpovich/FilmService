package by.karpovich.filmSevice.api.controller;

import by.karpovich.filmSevice.api.dto.FilmDto;
import by.karpovich.filmSevice.api.dto.searchCriteriaDto.FilmSearchCriteriaDto;
import by.karpovich.filmSevice.service.FilmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        FilmDto byId = filmService.findById(id);

        if (byId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @ApiOperation(value = "Find all films by criteria")
    @GetMapping
    public ResponseEntity<?> findByCriteria(FilmSearchCriteriaDto filmSearchCriteriaDto) {
        List<FilmDto> allByCriteria = filmService.findAllByCriteria(filmSearchCriteriaDto);

        if (allByCriteria.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allByCriteria, HttpStatus.OK);
    }

    @ApiOperation(value = "Find all films by criteria")
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<FilmDto> dtoList = filmService.findAll();

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "Save film")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody FilmDto filmDto) {
         filmService.save(filmDto);


        return new ResponseEntity<>("Film saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update Film by id")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody FilmDto filmDto,
                                    @PathVariable("id") Long id) {
        filmService.update(filmDto, id);

        return new ResponseEntity<>("Film updated successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Delete By id Film")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        filmService.deleteById(id);

        return new ResponseEntity<>("Film deleted successfully", HttpStatus.OK);
    }

}
