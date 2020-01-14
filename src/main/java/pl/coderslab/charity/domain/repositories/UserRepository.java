package pl.coderslab.charity.domain.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.domain.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // EAGER load of UserInfo
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"roles", "userInfo"})
    List<User> findAllWithRolesWthUserInfoByRoles(Role role);

    // EAGER load of UserInfo
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"roles", "userInfo"})
    User findAllById(Long id);

    User findAllByEmail(String email);

    // EAGER load of UserInfo
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"roles", "userInfo"})
    User findAllWithUserInfoByEmail(String email);
//    Similar query to above
//    @Query("select p from Person p where p.emailAddress = ?1")
//    @Query(value = "SELECT * FROM users u JOIN users_roles ur ON u.id = ur.user_id JOIN roles r ON ur.roles_id = r.id WHERE u.email = ?", nativeQuery = true)
//    User findAllWithRulesByEmailQuery(String email);

}
