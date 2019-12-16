package pl.coderslab.charity.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.domain.entities.Institution;

import java.util.List;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    Institution findFirstById(Long id);

    Institution findFirstByName(String name);

    @Query("SELECT i FROM Institution i ORDER BY i.name")
    List<Institution> findAllOrderByName();
//    List<Institution> findAllOrderedByName();

    @Query("SELECT i FROM Institution i WHERE i.name LIKE '_%' ORDER BY i.name")
    List<Institution> findAllNameNotEmptyOrderByName();
//    List<Institution> findAllNameNotNullOrderByName();


}
