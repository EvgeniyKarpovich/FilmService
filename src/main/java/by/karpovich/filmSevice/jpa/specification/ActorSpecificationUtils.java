package by.karpovich.filmSevice.jpa.specification;

import by.karpovich.filmSevice.jpa.model.ActorModel;
import by.karpovich.filmSevice.api.dto.searchCriteriaDto.ActorSearchCriteriaDto;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class ActorSpecificationUtils {

    public static Specification<ActorModel> findName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<ActorModel> findDate(Instant date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dateOfBirth"), date);
    }

    public static Specification<ActorModel> findInTheRangeOfDates(Instant startDate, Instant endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("dateOfBirth"), startDate, endDate);
    }

    public static Specification<ActorModel> findSurname(String surname) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("surname"), surname);
    }

    public static Specification<ActorModel> findCountry(Long countryId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("placeOfBirth").get("id"), countryId);
    }

    public static Specification<ActorModel> defaultSpecification() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("id"), 0);
    }

    public static Specification<ActorModel> createFromCriteria(ActorSearchCriteriaDto criteriaDto) {
        Specification<ActorModel> actorSpecification = defaultSpecification();

        if (criteriaDto.getName() != null) {
            actorSpecification = actorSpecification.and(findName(criteriaDto.getName()));
        }
        if (criteriaDto.getDate() != null) {
            actorSpecification = actorSpecification.and(findDate(criteriaDto.getDate()));
        }
        if (criteriaDto.getStartDate() != null && criteriaDto.getEndDate() != null) {
            actorSpecification = actorSpecification.and(findInTheRangeOfDates(criteriaDto.getStartDate(), criteriaDto.getEndDate()));
        }
        if (criteriaDto.getSurname() != null) {
            actorSpecification = actorSpecification.and(findSurname(criteriaDto.getSurname()));
        }
        if (criteriaDto.getCountryId() != null) {
            actorSpecification = actorSpecification.and(findCountry(criteriaDto.getCountryId()));
        }
        return actorSpecification;
    }
}
