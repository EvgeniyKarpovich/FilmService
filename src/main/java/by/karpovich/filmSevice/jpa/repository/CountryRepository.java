package by.karpovich.filmSevice.jpa.repository;

import by.karpovich.filmSevice.jpa.model.CountryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<CountryModel, Long> {

    Optional<CountryModel> findByName(String name);
}
