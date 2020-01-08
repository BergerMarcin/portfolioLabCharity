package pl.coderslab.charity.domain.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findAllById(Long id);

    User findAllByEmail(String email);

    // EAGER load of UserInfo
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"user_infos"})
    User findAllWithUserInfoByEmail(String email);

}
