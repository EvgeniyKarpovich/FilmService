package by.karpovich.filmSevice.api.controller;

import by.karpovich.filmSevice.api.dto.ActorDto;
import by.karpovich.filmSevice.api.dto.searchCriteriaDto.ActorSearchCriteriaDto;
import by.karpovich.filmSevice.service.ActorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ActorDto findById(@PathVariable("id") Long id) {
        return actorService.findById(id);
    }

    @ApiOperation(value = "Save actor")
    @PostMapping
    public void save(@RequestBody ActorDto actorDto) {
        actorService.save(actorDto);
    }

    @ApiOperation(value = "Update by Id actor")
    @PutMapping("/{id}")
    public void update(@RequestBody ActorDto actorDto,
                       @PathVariable("id") Long id) {
        actorService.update(actorDto, id);
    }

    @ApiOperation(value = "Find all actors by criteria")
    @GetMapping
    public List<ActorDto> findByCriteria(ActorSearchCriteriaDto actorSearchCriteriaDto) {
        return actorService.findByCriteria(actorSearchCriteriaDto);
    }

    @ApiOperation(value = "Delete by Id actor")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        actorService.deleteById(id);
    }

}
