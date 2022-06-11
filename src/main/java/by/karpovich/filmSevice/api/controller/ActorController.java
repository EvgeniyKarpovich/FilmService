package by.karpovich.filmSevice.api.controller;

import by.karpovich.filmSevice.api.dto.ActorDto;
import by.karpovich.filmSevice.api.dto.ActorDtoFull;
import by.karpovich.filmSevice.api.dto.searchCriteriaDto.ActorSearchCriteriaDto;
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

    @ApiOperation(value = "Find all actors with this Surname")
    @GetMapping("/names/{name}")
    public List<ActorModel> findBySurnameStartsWith(@PathVariable("name") String name) {
        return actorService.findByName(name);
    }


    @ApiOperation(value = "Find actor by Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        ActorDtoFull actorDtoFullById = actorService.findById(id);
        
        return new ResponseEntity<>(actorDtoFullById, HttpStatus.OK);
    }


    @ApiOperation(value = "Save actor")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody ActorDto actorDto) {
        ActorDto save = actorService.save(actorDto);

        if (save == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Actor saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update by Id actor")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody ActorDto actorDto,
                                    @PathVariable("id") Long id) {
        ActorDto update = actorService.update(actorDto, id);

        if (update == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Actor saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Find all actors by criteria")
    @GetMapping
    public ResponseEntity<?> findByCriteria(ActorSearchCriteriaDto actorSearchCriteriaDto) {
        List<ActorDto> byCriteria = actorService.findByCriteria(actorSearchCriteriaDto);

        if (byCriteria.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(byCriteria, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete by Id actor")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        actorService.deleteById(id);

        return new ResponseEntity<>("Film deleted successfully", HttpStatus.OK);
    }

}
