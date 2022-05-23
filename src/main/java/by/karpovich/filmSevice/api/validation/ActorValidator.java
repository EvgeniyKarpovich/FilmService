package by.karpovich.filmSevice.api.validation;

import by.karpovich.filmSevice.jpa.model.ActorModel;
import by.karpovich.filmSevice.jpa.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ActorValidator implements ConstraintValidator<ValidActor, Long> {

    @Autowired
    private ActorRepository actorRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return false;
        }
        Optional<ActorModel> model = actorRepository.findById(id);
        return model.isPresent();
    }
}
