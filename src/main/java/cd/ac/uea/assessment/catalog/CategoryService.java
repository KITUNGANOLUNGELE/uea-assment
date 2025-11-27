package cd.ac.uea.assessment.catalog;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
 
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        
        for (Category category : categories) {
            category.getProducts().size(); 
        }
        
        return categories;
    }
}
