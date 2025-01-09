package projects.gallery.photo_gallery.service.interfaces;

import projects.gallery.photo_gallery.dto.response.CategoryDto;
import projects.gallery.photo_gallery.dto.response.PhotoDto;

import java.util.List;

public interface MediaService {
    CategoryDto getCategoryById(Long id);
    List<CategoryDto> getAllCategories();
    List<PhotoDto> getPhotosOfCategory(Long categoryId);
    PhotoDto getPhotoById(Long id);
}
