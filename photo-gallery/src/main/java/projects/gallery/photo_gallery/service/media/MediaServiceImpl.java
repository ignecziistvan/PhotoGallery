package projects.gallery.photo_gallery.service.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final CategoryRepository categoryRepository;
    private final PhotoRepository photoRepository;
    private final MessageSource messageSource;

    @Autowired
    public MediaServiceImpl(CategoryRepository categoryRepository, PhotoRepository photoRepository, MessageSource messageSource) {
        this.categoryRepository = categoryRepository;
        this.photoRepository = photoRepository;
        this.messageSource = messageSource;
    }


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
                        () -> new NotFoundException(
                                messageSource.getMessage("photo-not-found", null, "Photo was not found", LocaleContextHolder.getLocale())
                        )
                )
        );
    }

    @Override
    public void createCategory(CategoryRequest dto) {
        if (dto.getName() != null && dto.getName().isBlank()) {
            throw new BadRequestException(messageSource.getMessage(
                    "category-name-cannot-be-empty",
                    null,
                    "Category name cannot be empty",
                    LocaleContextHolder.getLocale()
            ));
        }

        Category category = new Category(dto.getName());

        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            if (dto.getDescription().length() > 500) {
                throw new BadRequestException(messageSource.getMessage(
                        "max-length-description-500",
                        null,
                        "Max length for description is 500",
                        LocaleContextHolder.getLocale()
                ));
            } else category.setDescription(dto.getDescription());
        }

        if (categoryRepository.findByAccessUrl(category.getAccessUrl()).isPresent()) {
            throw new BadRequestException(messageSource.getMessage(
                    "category-already-exists-by-access-url",
                    null,
                    "This category already exists by Access-URL",
                    LocaleContextHolder.getLocale()
            ));
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
                    () -> new NotFoundException(messageSource.getMessage(
                            "thumbnail-photo-not-found",
                            null,
                            "Thumbnail photo was not found by ID",
                            LocaleContextHolder.getLocale()
                    ))
            );

            if (!category.getPhotos().contains(thumbPhoto)) {
                throw new BadRequestException(messageSource.getMessage(
                        "photo-not-belong-to-category",
                        null,
                        "The selected photo does not belong to this category",
                        LocaleContextHolder.getLocale()
                ));
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
            throw new BadRequestException(messageSource.getMessage(
                    "nothing-to-change",
                    null,
                    "Nothing to change",
                    LocaleContextHolder.getLocale()
            ));
        }
    }

    private Category findCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(messageSource.getMessage(
                        "category-not-found",
                        null,
                        "Category was not found",
                        LocaleContextHolder.getLocale()
                ))
        );
    }
}
