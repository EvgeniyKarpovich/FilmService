package by.karpovich.filmSevice.api.controller;

import by.karpovich.filmSevice.api.dto.CountryDto;
import by.karpovich.filmSevice.service.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
@Api(tags = "Country Controller")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @ApiOperation(value = "Find country by id")
    @GetMapping("/{id}")
    public CountryDto findById(@PathVariable("id") Long id) {
        return countryService.findById(id);
    }

    @ApiOperation(value = "Find All country")
    @GetMapping
    public List<CountryDto> findAll() {
        return countryService.findAll();
    }

    @ApiOperation(value = "Save country")
    @PostMapping
    public void save(@RequestBody CountryDto countryDto) {
        countryService.save(countryDto);
    }

    @ApiOperation(value = "Update by id country")
    @PutMapping("/{id}")
    public void update(@RequestBody CountryDto countryDto,
                       @PathVariable("id") Long id) {
        countryService.update(id, countryDto);
    }

    @ApiOperation(value = "Delete by Id country")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        countryService.deleteById(id);
    }

    @ApiOperation(value = "Find country by Name")
    @GetMapping("/searchByName/{name}")
    public CountryDto findByName(@PathVariable("name") String name) {
        return countryService.findByName(name);
    }

}
