package by.karpovich.filmSevice.api.validation;

import by.karpovich.filmSevice.jpa.model.MusicModel;
import by.karpovich.filmSevice.jpa.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class MusicValidator implements ConstraintValidator<ValidMusic, Long> {

    @Autowired
    private MusicRepository musicRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return false;
        }
        Optional<MusicModel> model = musicRepository.findById(id);
        return model.isPresent();
    }
}
