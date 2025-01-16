package projects.gallery.photo_gallery.service.interfaces;

import projects.gallery.photo_gallery.dto.request.CategoryRequest;
import projects.gallery.photo_gallery.dto.response.CategoryResponse;
import projects.gallery.photo_gallery.dto.response.PhotoResponse;

import java.util.List;

public interface MediaService {
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    List<PhotoResponse> getPhotosOfCategory(Long categoryId);
    PhotoResponse getPhotoById(Long id);

    void createCategory(CategoryRequest dto);
    void deleteCategory(Long categoryId);
    void patchCategory(CategoryRequest dto, Long categoryId);
}
