package pl.coderslab.charity.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findAllById(Long id);

    User findAllByEmail(String email);

}
