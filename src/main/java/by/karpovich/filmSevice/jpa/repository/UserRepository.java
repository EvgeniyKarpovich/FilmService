package by.karpovich.filmSevice.jpa.repository;

import by.karpovich.filmSevice.jpa.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

}
