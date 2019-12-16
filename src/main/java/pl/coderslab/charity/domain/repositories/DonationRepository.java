package pl.coderslab.charity.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.domain.entities.Category;
import pl.coderslab.charity.domain.entities.Donation;
import pl.coderslab.charity.domain.entities.Institution;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    Donation findFirstById(Long id);

    Donation findFirstByInstitutionOrderByInstitutionName(Institution institution);

    @Query("SELECT d FROM Donation d ORDER BY d.pickUpDate, d.pickUpTime")
    List<Donation> findAllOrderedByPickUpDateAndPickUpTime();
//    List<Donation> findAllOrderedByPickUpDateAndPickUpTime();

}
