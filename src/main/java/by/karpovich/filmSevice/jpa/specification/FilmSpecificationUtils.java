package by.karpovich.filmSevice.jpa.specification;

import by.karpovich.filmSevice.api.dto.searchCriteriaDto.FilmSearchCriteriaDto;
import by.karpovich.filmSevice.jpa.model.FilmModel;
import by.karpovich.filmSevice.jpa.model.GenreForFilm;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class FilmSpecificationUtils {
    public static Specification<FilmModel> findName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<FilmModel> findDate(Instant date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("releaseDate"), date);
    }

    public static Specification<FilmModel> findInTheRangeOfDates(Instant startDate, Instant endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("releaseDate"), startDate, endDate);
    }

    public static Specification<FilmModel> findGenre(GenreForFilm genre) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("genre"), genre);
    }

    public static Specification<FilmModel> findCountry(Long countryId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("country").get("id"), countryId);
    }

    public static Specification<FilmModel> findDirector(Long directorId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("director").get("id"), directorId);
    }

    public static Specification<FilmModel> defaultSpecification() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("id"), 0);
    }

    public static Specification<FilmModel> createFromCriteria(FilmSearchCriteriaDto criteriaDto) {
        Specification<FilmModel> filmSpecification = defaultSpecification();

        if (criteriaDto.getName() != null) {
            filmSpecification = filmSpecification.and(findName(criteriaDto.getName()));
        }
        if (criteriaDto.getDate() != null) {
            filmSpecification = filmSpecification.and(findDate(criteriaDto.getDate()));
        }
        if (criteriaDto.getStartDate() != null && criteriaDto.getEndDate() != null) {
            filmSpecification = filmSpecification.and(findInTheRangeOfDates(criteriaDto.getStartDate(), criteriaDto.getEndDate()));
        }
        if (criteriaDto.getGenre() != null) {
            filmSpecification = filmSpecification.and(findGenre(criteriaDto.getGenre()));
        }
        if (criteriaDto.getCountryId() != null) {
            filmSpecification = filmSpecification.and(findCountry(criteriaDto.getCountryId()));
        }
        if (criteriaDto.getDirectorId() != null) {
            filmSpecification = filmSpecification.and(findDirector(criteriaDto.getDirectorId()));
        }
        return filmSpecification;
    }
}
