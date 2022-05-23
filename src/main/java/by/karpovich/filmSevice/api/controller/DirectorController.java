package by.karpovich.filmSevice.api.controller;

import by.karpovich.filmSevice.api.dto.DirectorDto;
import by.karpovich.filmSevice.service.DirectorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directors")

@Api(tags = "Director Controller")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @ApiOperation(value = "Find by Id Director")
    @GetMapping("/{id}")
    public DirectorDto findById(@PathVariable("id") Long id) {
        return directorService.findById(id);
    }

    @ApiOperation(value = "Find all directors")
    @GetMapping
    public List<DirectorDto> findAll() {
        return directorService.findAll();
    }

    @ApiOperation(value = "Save Director")
    @PostMapping
    public DirectorDto save(@RequestBody DirectorDto directorDto) {
        return directorService.save(directorDto);
    }

    @ApiOperation(value = "Update director by id")
    @PutMapping("/{id}")
    public DirectorDto update(@RequestBody DirectorDto directorDto,
                              @PathVariable("id") Long id) {
        return directorService.update(directorDto, id);
    }

    @ApiOperation(value = "Delete director by Id")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        directorService.deleteById(id);
    }

}
