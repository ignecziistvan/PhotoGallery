package projects.gallery.photo_gallery.service;

import org.springframework.stereotype.Service;
import projects.gallery.photo_gallery.dto.response.CategoryDto;
import projects.gallery.photo_gallery.dto.response.PhotoDto;

import java.util.List;

@Service
public class MediaServiceImpl implements MediaService {
    @Override
    public CategoryDto getCategoryById(Long id) {
        return null;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return List.of();
    }

    @Override
    public List<PhotoDto> getPhotosOfCategory(Long categoryId) {
        return List.of();
    }
}
