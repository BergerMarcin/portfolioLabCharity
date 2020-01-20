package pl.coderslab.charity.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Category;
import pl.coderslab.charity.domain.repositories.CategoryRepository;
import pl.coderslab.charity.dtos.CategoryDataDTO;
import pl.coderslab.charity.services.CategoryService;
import pl.coderslab.charity.services.Mapper;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDataDTO> allCategoryDataDTOList() {
        List<Category> categoryList = categoryRepository.findAllByNameIsNotNullOrderByName();
        Mapper<Category, CategoryDataDTO> mapper = new Mapper<>();
        return mapper.mapList(categoryList, new CategoryDataDTO(), "STANDARD");

//        List<CategoryDataDTO> categoryDataDTOList = new ArrayList<>();
//        for (Category category: categoryList) {
//            ModelMapper modelMapper = new ModelMapper();
//            CategoryDataDTO categoryDataDTO = modelMapper.map(category, CategoryDataDTO.class);
//            categoryDataDTOList.add(categoryDataDTO);
//        }
//        return categoryDataDTOList;
    }

}
