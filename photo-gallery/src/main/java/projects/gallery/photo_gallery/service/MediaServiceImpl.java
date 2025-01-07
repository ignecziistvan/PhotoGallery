package projects.gallery.photo_gallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projects.gallery.photo_gallery.dto.response.CategoryDto;
import projects.gallery.photo_gallery.dto.response.PhotoDto;
import projects.gallery.photo_gallery.exception.NotFoundException;
import projects.gallery.photo_gallery.model.Category;
import projects.gallery.photo_gallery.repository.CategoryRepository;
import projects.gallery.photo_gallery.repository.PhotoRepository;

import java.util.List;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public CategoryDto getCategoryById(Long id) {
        return new CategoryDto(findCategory(id));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryDto::new).toList();
    }

    @Override
    public List<PhotoDto> getPhotosOfCategory(Long categoryId) {
        return findCategory(categoryId).getPhotos().stream().map(PhotoDto::new).toList();
    }

    @Override
    public PhotoDto getPhotoById(Long id) {
        return new PhotoDto(
                photoRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Photo was not found")
                )
        );
    }

    private Category findCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Category was not found")
        );
    }
}
