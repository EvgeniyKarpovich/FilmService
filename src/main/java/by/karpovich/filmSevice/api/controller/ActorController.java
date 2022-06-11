package by.karpovich.filmSevice.api.controller;

import by.karpovich.filmSevice.api.dto.ActorDtoFull;
import by.karpovich.filmSevice.api.dto.ActorForSaveDto;
import by.karpovich.filmSevice.jpa.model.ActorModel;
import by.karpovich.filmSevice.service.ActorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actors")
@Api(tags = "Actor Controller")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @ApiOperation(value = "Find actor by Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        ActorDtoFull actorDtoFullById = actorService.findActorById(id);

        return new ResponseEntity<>(actorDtoFullById, HttpStatus.OK);
    }

    @ApiOperation(value = "Save actor")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody ActorForSaveDto actorDto) {
        actorService.save(actorDto);

        return new ResponseEntity<>("Actor saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update by Id actor")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody ActorForSaveDto actorDto,
                                    @PathVariable("id") Long id) {
         actorService.update(actorDto, id);

        return new ResponseEntity<>("Actor updated successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Find all actors by criteria")
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<ActorDtoFull> all = actorService.findAll();

        if (all.isEmpty()) {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete by Id actor")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        actorService.deleteById(id);

        return new ResponseEntity<>("Actor deleted successfully", HttpStatus.OK);
    }

}
