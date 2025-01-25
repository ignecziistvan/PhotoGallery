package projects.gallery.photo_gallery.service.interfaces.media;


import jakarta.validation.Valid;
import projects.gallery.photo_gallery.dto.request.CategoryRequest;
import projects.gallery.photo_gallery.model.media.Category;

public interface AuthenticatedCategoryService {
    Category createCategory(@Valid CategoryRequest dto);
    void deleteCategory(Long categoryId);
    Category patchCategory(@Valid CategoryRequest dto, Long categoryId);
}
