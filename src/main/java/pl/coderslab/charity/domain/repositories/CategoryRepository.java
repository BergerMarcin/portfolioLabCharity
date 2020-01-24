package pl.coderslab.charity.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.domain.entities.Category;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findAllById(Long id);

    List<Category> findAllByIdIn(List<Long> ids);

    Category findAllByName(String name);

    List<Category> findAllByOrderByName();
//    @Query("SELECT c FROM Category c ORDER BY c.name")
//    List<Category> findAllByOrderByName();

    List<Category> findAllByNameIsNotNullOrderByName();
//    @Query("SELECT c FROM Category c WHERE c.name LIKE '_%' ORDER BY c.name")
//    List<Category> findAllByNameIsNotEmptyOrderByName();
}
