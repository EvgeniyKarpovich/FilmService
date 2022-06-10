package by.karpovich.filmSevice.jpa.repository;

import by.karpovich.filmSevice.jpa.model.MusicModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<MusicModel, Long>, PagingAndSortingRepository<MusicModel, Long> {

    //    Optional<MusicModel> findByNameIgnoreCase(String name);
//
//    List<MusicModel> findByNameStartsWithIgnoreCase(String name);
//
//    List<MusicModel> findByGenre(GenreMusic genre);
//
//    List<MusicModel> findByAuthorStartsWithIgnoreCase(String name);
//
//    List<MusicModel> findByAuthorIgnoreCaseAndGenre(String name, GenreMusic genreMusic);
//
    Page<MusicModel> findAll(Pageable pageable);

    Optional<MusicModel> findByNameAndAuthor(String name, String author);
}
