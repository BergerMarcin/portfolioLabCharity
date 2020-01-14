package pl.coderslab.charity.services;

import pl.coderslab.charity.domain.entities.Category;
import pl.coderslab.charity.dtos.CategoryDataDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDataDTO> allCategoryDataDTOList ();

}
