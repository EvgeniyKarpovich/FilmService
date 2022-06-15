package by.karpovich.filmSevice.jpa.repository;

import by.karpovich.filmSevice.jpa.model.FilmModel;
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
public interface FilmRepository extends JpaRepository<FilmModel, Long>, JpaSpecificationExecutor<FilmModel>,
        PagingAndSortingRepository<FilmModel, Long> {

    @Query(nativeQuery = true, value = "SELECT actor_id FROM ACTORS_FILMS where film_id = :film")
    List<Long> getActorsIdByFilmId(@Param("film") Long id);

    @Query(nativeQuery = true, value = "SELECT music_id FROM MUSICS_FILMS where film_id = :film")
    List<Long> getMusicsIdByFilmId(@Param("film") Long id);

    Page<FilmModel> findAll(Pageable pageable);


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
