package by.karpovich.filmSevice.api.controller;

import by.karpovich.filmSevice.api.dto.DirectorDto;
import by.karpovich.filmSevice.service.DirectorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/directors")

@Api(tags = "Director Controller")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @ApiOperation(value = "Find by Id Director")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        DirectorDto byId = directorService.findById(id);

        if (byId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @ApiOperation(value = "Find all directors")
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<DirectorDto> all = directorService.findAll();

        if (all.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @ApiOperation(value = "Save Director")
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody DirectorDto directorDto) {
        DirectorDto save = directorService.save(directorDto);

        if (save == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Director saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update director by id")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody DirectorDto directorDto,
                                    @PathVariable("id") Long id) {
        DirectorDto update = directorService.update(directorDto, id);

        if (update == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Director saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Delete director by Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        directorService.deleteById(id);

        return new ResponseEntity<>("Director deleted successfully", HttpStatus.OK);

    }

}
