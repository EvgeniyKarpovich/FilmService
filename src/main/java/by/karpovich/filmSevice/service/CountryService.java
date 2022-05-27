package by.karpovich.filmSevice.service;

import by.karpovich.filmSevice.api.dto.CountryDto;
import by.karpovich.filmSevice.exception.DuplicateException;
import by.karpovich.filmSevice.exception.NotFoundModelException;
import by.karpovich.filmSevice.jpa.model.CountryModel;
import by.karpovich.filmSevice.jpa.repository.CountryRepository;
import by.karpovich.filmSevice.mapping.CountryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CountryMapper countryMapper;

    public CountryDto save(CountryDto countryDto) {
        validateAlreadyExists(countryDto, null);
        CountryModel entity = countryMapper.mapFromDto(countryDto);
        CountryModel savedCountry = countryRepository.save(entity);
        log.info("IN save -  Country with name '{}' saved", countryDto.getName());
        return countryMapper.mapFromEntity(savedCountry);
    }

    public CountryDto findById(Long id) {
        Optional<CountryModel> optionalCountry = countryRepository.findById(id);
        CountryModel country = optionalCountry.orElseThrow(
                () -> new NotFoundModelException(String.format("Country with id = %s not found", id)));
        log.info("IN findById -  Country with id = {} found", country.getId());
        return countryMapper.mapFromEntity(country);
    }

    public List<CountryDto> findAll() {
        List<CountryModel> countryList = countryRepository.findAll();
        log.info("IN findAll - the number countries = {}", countryList.size());
        return countryMapper.mapFromListEntity(countryList);
    }

    public CountryDto update(Long id, CountryDto countryDto) {
        validateAlreadyExists(countryDto, id);
        CountryModel country = countryMapper.mapFromDto(countryDto);
        country.setId(id);
        CountryModel updated = countryRepository.save(country);
        log.info("IN update -  Film '{}' updated", countryDto.getName());
        return countryMapper.mapFromEntity(updated);
    }

    public void deleteById(Long id) {
        if (countryRepository.findById(id).isPresent()) {
            countryRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format("Country with id = %s not found", id));
        }
        log.info("IN deleteById - Country with id = {} deleted", id);
    }

    private void validateAlreadyExists(CountryDto countryDto, Long id) {
        Optional<CountryModel> country = countryRepository.findByName(countryDto.getName());
        if (country.isPresent() && !country.get().getId().equals(id)) {
            throw new DuplicateException(String.format("Country with id = %s already exist", id));
        }
    }

    public CountryDto findByName(String name) {
        Optional<CountryModel> optionalCountry = countryRepository.findByName(name);
        CountryModel country = optionalCountry.orElseThrow(
                () -> new NotFoundModelException(String.format("Country with name = %s not found", name)));
        log.info("IN findById -  Country with name = {} found", country.getName());
        return countryMapper.mapFromEntity(country);
    }

    public CountryModel findByIdWhichWillReturnModel(Long id) {
        Optional<CountryModel> optionalCountry = countryRepository.findById(id);
        return optionalCountry.orElseThrow(
                () -> new NotFoundModelException("Country with ID = " + id + " not found"));
    }
}
