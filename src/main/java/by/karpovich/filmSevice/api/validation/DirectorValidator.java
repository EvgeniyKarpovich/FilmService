package by.karpovich.filmSevice.api.validation;

import by.karpovich.filmSevice.jpa.model.DirectorModel;
import by.karpovich.filmSevice.jpa.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class DirectorValidator implements ConstraintValidator<ValidDirector, Long> {

    @Autowired
    private DirectorRepository directorRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return false;
        }
        Optional<DirectorModel> model = directorRepository.findById(id);
        return model.isPresent();
    }
}
