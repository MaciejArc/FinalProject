package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {



    Optional<User> findUserByEmail(String email);
    Boolean existsUserByEmail(String email);

    List<User> findUserByRole(String role);


}
