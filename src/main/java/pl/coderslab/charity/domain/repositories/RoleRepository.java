package pl.coderslab.charity.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.domain.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findAllByName(String name);
}
