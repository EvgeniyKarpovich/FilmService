package by.karpovich.filmSevice.jpa.repository;

import by.karpovich.filmSevice.jpa.model.ActorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<ActorModel, Long>, JpaSpecificationExecutor<ActorModel> {

    List<ActorModel> findByNameStartsWithIgnoreCase(String name);

    Optional<ActorModel> findByNameStartsWithIgnoreCaseAndSurnameStartsWithIgnoreCase(String name, String surname);

    List<ActorModel> findBySurnameStartsWithIgnoreCase(String name);

    List<ActorModel> findByPlaceOfBirthNameStartsWithIgnoreCase(String countryName);

    List<ActorModel> findByDateOfBirthAfter(Instant date);

    List<ActorModel> findByHeightGreaterThanEqual(Integer height);

    List<ActorModel> findByAwardsGreaterThanEqual(Integer awards);

    Optional<ActorModel> findByNameAndSurname(String name, String surname);
}
