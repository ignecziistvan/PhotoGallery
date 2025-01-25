package projects.gallery.photo_gallery.service.interfaces.media;

import projects.gallery.photo_gallery.dto.request.CategoryRequest;
import projects.gallery.photo_gallery.dto.response.CategoryResponse;
import projects.gallery.photo_gallery.dto.response.PhotoResponse;
import projects.gallery.photo_gallery.model.media.Category;

import java.util.List;

@Deprecated
public interface MediaService {
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    List<PhotoResponse> getPhotosOfCategory(Long categoryId);
    PhotoResponse getPhotoById(Long id);

    Category createCategory(CategoryRequest dto);
    void deleteCategory(Long categoryId);
    void patchCategory(CategoryRequest dto, Long categoryId);
}
