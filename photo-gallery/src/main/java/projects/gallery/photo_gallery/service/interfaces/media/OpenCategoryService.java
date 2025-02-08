package projects.gallery.photo_gallery.service.interfaces.media;

import projects.gallery.photo_gallery.dto.response.CategoryResponse;
import projects.gallery.photo_gallery.dto.response.PhotoResponse;

import java.util.List;

public interface OpenCategoryService {
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
}
