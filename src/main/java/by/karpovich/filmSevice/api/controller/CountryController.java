package by.karpovich.filmSevice.api.controller;

import by.karpovich.filmSevice.api.dto.CountryDto;
import by.karpovich.filmSevice.jpa.model.CountryModel;
import by.karpovich.filmSevice.jpa.repository.CountryRepository;
import by.karpovich.filmSevice.mapping.CountryMapper;
import by.karpovich.filmSevice.service.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/countries")
@Api(tags = "Country Controller")
public class CountryController {

    @Autowired
    private CountryService countryService;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CountryMapper countryMapper;

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
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "2") int size) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
            Page<CountryModel> countryModelPage = countryRepository.findAll(pageable);
            List<CountryModel> content = countryModelPage.getContent();

            List<CountryDto> countryDtoList = countryMapper.mapFromListEntity(content);

            Map<String, Object> response = new HashMap<>();
            response.put("tutorials", countryDtoList);
            response.put("currentPage", countryModelPage.getNumber());
            response.put("totalItems", countryModelPage.getTotalElements());
            response.put("totalPages", countryModelPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
