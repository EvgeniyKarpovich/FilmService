package by.karpovich.filmSevice.jpa.repository;

import by.karpovich.filmSevice.jpa.model.ActorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<ActorModel, Long>, JpaSpecificationExecutor<ActorModel> {

    List<ActorModel> findByNameStartingWith(String name);

    @Query(nativeQuery = true, value = "SELECT film_id FROM ACTORS_FILMS where actor_id = :film")
    List<Long> getFilmId(@Param("film") Long id);

//    Optional<ActorModel> findByNameStartsWithIgnoreCaseAndSurnameStartsWithIgnoreCase(String name, String surname);
//
//    List<ActorModel> findBySurnameStartsWithIgnoreCase(String name);
//
//    List<ActorModel> findByPlaceOfBirthNameStartsWithIgnoreCase(String countryName);
//
//    List<ActorModel> findByDateOfBirthAfter(Instant date);
//
//    List<ActorModel> findByHeightGreaterThanEqual(Integer height);
//
//    List<ActorModel> findByAwardsGreaterThanEqual(Integer awards);

    Optional<ActorModel> findByNameAndLastname(String name, String surname);
}
