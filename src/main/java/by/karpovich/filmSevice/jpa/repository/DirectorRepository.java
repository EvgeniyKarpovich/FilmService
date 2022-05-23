package by.karpovich.filmSevice.jpa.repository;

import by.karpovich.filmSevice.jpa.model.DirectorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<DirectorModel, Long> {

    List<DirectorModel> findByNameStartsWithIgnoreCase(String name);

    List<DirectorModel> findBySurnameStartsWithIgnoreCase(String name);

    List<DirectorModel> findByPlaceOfBirthNameStartsWithIgnoreCase(String countryName);

    List<DirectorModel> findByHeightGreaterThanEqual(Integer height);

    List<DirectorModel> findByNameStartsWithIgnoreCaseAndSurnameStartsWithIgnoreCase(String name, String surname);

    List<DirectorModel> findByDateOfBirth(Instant date);

    Optional<DirectorModel> findByNameAndSurname(String name, String surname);
}
