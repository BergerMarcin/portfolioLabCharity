package pl.coderslab.charity.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.domain.entities.Category;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findFirstById(Long id);

    Category findFirstByName(String name);

    @Query("SELECT c FROM Category c ORDER BY c.name")
    List<Category> findAllOrderByName();
//    List<Category> findAllOrderedByName();

    @Query("SELECT c FROM Category c WHERE c.name LIKE '_%' ORDER BY c.name")
    List<Category> findAllNameNotEmptyOrderByName();
//    List<Category> findAllNameNotNullOrderByName();
}
