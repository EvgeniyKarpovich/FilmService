package by.karpovich.filmSevice.jpa.repository;

import by.karpovich.filmSevice.jpa.model.FilmModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<FilmModel, Long>, JpaSpecificationExecutor<FilmModel> {

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
