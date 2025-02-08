package projects.gallery.photo_gallery.service.media.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import projects.gallery.photo_gallery.dto.response.CategoryResponse;
import projects.gallery.photo_gallery.exception.NotFoundException;
import projects.gallery.photo_gallery.model.media.Category;
import projects.gallery.photo_gallery.repository.media.CategoryRepository;
import projects.gallery.photo_gallery.service.interfaces.media.OpenCategoryService;

import java.util.List;

@Service
public class OpenCategoryServiceImpl implements OpenCategoryService {
    private final CategoryRepository categoryRepository;
    private final MessageSource messageSource;

    @Autowired
    public OpenCategoryServiceImpl(CategoryRepository categoryRepository, MessageSource messageSource) {
        this.categoryRepository = categoryRepository;
        this.messageSource = messageSource;
    }


    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(messageSource.getMessage(
                        "category-not-found",
                        null,
                        "Category was not found",
                        LocaleContextHolder.getLocale()
                ))
        );
        return new CategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryResponse::new).toList();
    }
}
