package by.karpovich.filmSevice.jpa.repository;

import by.karpovich.filmSevice.jpa.model.FilmModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<FilmModel, Long>, JpaSpecificationExecutor<FilmModel> {

    @Query(nativeQuery = true, value = "SELECT actor_id FROM ACTORS_FILMS where film_id = :film")
    List<Long> getActorsId(@Param("film") Long id);

    @Query(nativeQuery = true, value = "SELECT music_id FROM MUSICS_FILMS where film_id = :film")
    List<Long> getMusicsId(@Param("film") Long id);

    //    List<FilmModel> findByNameStartsWithIgnoreCase(String name);
//
//    List<FilmModel> findByCountryNameStartsWithIgnoreCase(String country);
//
//    List<FilmModel> findByReleaseDateAfter(Instant date);
//
//    List<FilmModel> findByDirectorNameStartsWithIgnoreCase(String name);
//
//    List<FilmModel> findDistinctByGenre(GenreForFilm genre);
//
//    List<FilmModel> findByGenreAndDirectorNameStartsWithIgnoreCase(GenreForFilm genre, String name);
//
//    List<FilmModel> findByGenreAndHavingAnOskar(GenreForFilm genre, Boolean havingOskar);
//
    Optional<FilmModel> findByNameAndDirectorId(String name, Long id);

}
