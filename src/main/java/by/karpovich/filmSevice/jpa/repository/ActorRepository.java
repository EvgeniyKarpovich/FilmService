package by.karpovich.filmSevice.jpa.repository;

import by.karpovich.filmSevice.jpa.model.ActorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<ActorModel, Long>,
        JpaSpecificationExecutor<ActorModel>, PagingAndSortingRepository<ActorModel, Long> {

    Optional<ActorModel> findByNameAndLastname(String name, String surname);

    @Query(nativeQuery = true, value = "SELECT film_id FROM ACTORS_FILMS where actor_id = :actor")
    List<Long> getFilmsIdByActorId(@Param("actor") Long id);

    Page<ActorModel> findAll(Pageable pageable);

    List<ActorModel> findByNameStartingWith(String name);

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
}
