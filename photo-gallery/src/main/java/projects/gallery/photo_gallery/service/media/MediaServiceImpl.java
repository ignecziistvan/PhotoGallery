package projects.gallery.photo_gallery.service.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projects.gallery.photo_gallery.dto.request.CategoryRequest;
import projects.gallery.photo_gallery.dto.response.CategoryResponse;
import projects.gallery.photo_gallery.dto.response.PhotoResponse;
import projects.gallery.photo_gallery.exception.BadRequestException;
import projects.gallery.photo_gallery.exception.NotFoundException;
import projects.gallery.photo_gallery.model.media.Category;
import projects.gallery.photo_gallery.model.media.Photo;
import projects.gallery.photo_gallery.repository.media.CategoryRepository;
import projects.gallery.photo_gallery.repository.media.PhotoRepository;
import projects.gallery.photo_gallery.service.interfaces.MediaService;

import java.util.List;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public CategoryResponse getCategoryById(Long id) {
        return new CategoryResponse(findCategory(id));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryResponse::new).toList();
    }

    @Override
    public List<PhotoResponse> getPhotosOfCategory(Long categoryId) {
        return findCategory(categoryId).getPhotos().stream().map(PhotoResponse::new).toList();
    }

    @Override
    public PhotoResponse getPhotoById(Long id) {
        return new PhotoResponse(
                photoRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Photo was not found")
                )
        );
    }

    @Override
    public void createCategory(CategoryRequest dto) {
        if (dto.getName() != null && dto.getName().isBlank()) {
            throw new BadRequestException("Category name cannot be empty");
        }

        Category category = new Category(dto.getName());

        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            if (dto.getDescription().length() > 500) {
                throw new BadRequestException("Max length for description is 500 characters");
            } else category.setDescription(dto.getDescription());
        }

        if (categoryRepository.findByAccessUrl(category.getAccessUrl()).isPresent()) {
            throw new BadRequestException("This category already exists by Access-URL");
        }

        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = findCategory(categoryId);
        categoryRepository.delete(category);
    }

    @Override
    public void patchCategory(CategoryRequest dto, Long categoryId) {
        Category category = findCategory(categoryId);
        boolean isChanged = false;

        if (dto.getName() != null && !dto.getName().isBlank() && !dto.getName().equals(category.getName())) {
            category.setName(dto.getName());
            isChanged = true;
        }
        if (dto.getDescription() != null && !dto.getDescription().isBlank() && !dto.getDescription().equals(category.getDescription())) {
            category.setDescription(dto.getDescription());
            isChanged = true;
        }

        if (dto.getThumbnailPhotoId() != null) {
            Photo thumbPhoto = photoRepository.findById(dto.getThumbnailPhotoId()).orElseThrow(
                    () -> new NotFoundException("Thumbnail photo was not found by ID")
            );

            if (!category.getPhotos().contains(thumbPhoto)) {
                throw new BadRequestException("The selected photo is not related to this category");
            }

            if (category.getThumbnailPhoto() == null) {
                category.setThumbnailPhoto(thumbPhoto);
                isChanged = true;
            } else if (!category.getThumbnailPhoto().equals(thumbPhoto)) {
                category.setThumbnailPhoto(thumbPhoto);
                isChanged = true;
            }
        }

        if (isChanged) {
            categoryRepository.save(category);
        } else {
            throw new BadRequestException("Nothing to change");
        }
    }

    private Category findCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Category was not found")
        );
    }
}
