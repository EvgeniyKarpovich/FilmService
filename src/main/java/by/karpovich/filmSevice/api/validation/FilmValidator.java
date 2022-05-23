package by.karpovich.filmSevice.api.validation;

import by.karpovich.filmSevice.jpa.model.FilmModel;
import by.karpovich.filmSevice.jpa.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class FilmValidator implements ConstraintValidator<ValidFilm, Long> {
    @Autowired
    private FilmRepository filmRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return false;
        }
        Optional<FilmModel> model = filmRepository.findById(id);
        return model.isPresent();
    }
}
