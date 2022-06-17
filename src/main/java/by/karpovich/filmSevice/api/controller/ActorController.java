package by.karpovich.filmSevice.api.controller;

import by.karpovich.filmSevice.api.dto.ActorDto;
import by.karpovich.filmSevice.api.dto.ActorDtoFull;
import by.karpovich.filmSevice.service.ActorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/actors")
@Api(tags = "Actor Controller")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @ApiOperation(value = "Find actor by Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        ActorDto actorDtoById = actorService.findById(id);

        return new ResponseEntity<>(actorDtoById, HttpStatus.OK);
    }

    @ApiOperation(value = "Save actor")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody ActorDto actorDto) {
        actorService.save(actorDto);

        return new ResponseEntity<>("Actor saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update by Id actor")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody ActorDto actorDto,
                                    @PathVariable("id") Long id) {
        actorService.update(actorDto, id);

        return new ResponseEntity<>("Actor updated successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Find all actors by criteria")
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> all = actorService.findAll(page, size);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete by Id actor")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        actorService.deleteById(id);

        return new ResponseEntity<>("Actor deleted successfully", HttpStatus.OK);
    }

}
