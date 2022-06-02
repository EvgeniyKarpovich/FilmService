package by.karpovich.filmSevice.api.controller;

import by.karpovich.filmSevice.api.dto.CountryDto;
import by.karpovich.filmSevice.service.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/countries")
@Api(tags = "Country Controller")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @ApiOperation(value = "Find country by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        CountryDto byId = countryService.findById(id);

        if (byId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @ApiOperation(value = "Find All country")
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<CountryDto> all = countryService.findAll();

        if (all.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(all, HttpStatus.OK);

    }

    @ApiOperation(value = "Save country")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid CountryDto countryDto) {
        CountryDto save = countryService.save(countryDto);

        if (save == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Country saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update by id country")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CountryDto countryDto,
                                    @PathVariable("id") Long id) {
        CountryDto update = countryService.update(id, countryDto);

        if (update == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Country saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Delete by Id country")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        countryService.deleteById(id);

        return new ResponseEntity<>("Country deleted successfully", HttpStatus.OK);
    }

}
