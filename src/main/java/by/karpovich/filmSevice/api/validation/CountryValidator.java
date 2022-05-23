package by.karpovich.filmSevice.api.validation;

import by.karpovich.filmSevice.jpa.model.CountryModel;
import by.karpovich.filmSevice.jpa.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class CountryValidator implements ConstraintValidator<ValidCountry, Long> {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return false;
        }
        Optional<CountryModel> model = countryRepository.findById(id);
        return model.isPresent();
    }
}
