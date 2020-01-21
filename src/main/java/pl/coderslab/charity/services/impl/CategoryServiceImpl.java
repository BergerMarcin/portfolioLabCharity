package pl.coderslab.charity.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Category;
import pl.coderslab.charity.domain.repositories.CategoryRepository;
import pl.coderslab.charity.dtos.CategoryDTO;
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
    public List<CategoryDTO> allCategoryDTOList() {
        List<Category> categoryList = categoryRepository.findAllByNameIsNotNullOrderByName();
        Mapper<Category, CategoryDTO> mapper = new Mapper<>();
        return mapper.mapList(categoryList, new CategoryDTO(), "STANDARD");
    }

}
