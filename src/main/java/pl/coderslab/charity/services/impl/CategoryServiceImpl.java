package pl.coderslab.charity.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Category;
import pl.coderslab.charity.domain.repositories.CategoryRepository;
import pl.coderslab.charity.services.CategoryService;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> allCategoryList() {
        return categoryRepository.findAllByNameIsNotNullOrderByName();
    }
}
