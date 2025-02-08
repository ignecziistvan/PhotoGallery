package projects.gallery.photo_gallery.service.media.photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import projects.gallery.photo_gallery.dto.response.PhotoResponse;
import projects.gallery.photo_gallery.exception.NotFoundException;
import projects.gallery.photo_gallery.model.media.Category;
import projects.gallery.photo_gallery.model.media.Photo;
import projects.gallery.photo_gallery.repository.media.CategoryRepository;
import projects.gallery.photo_gallery.repository.media.PhotoRepository;
import projects.gallery.photo_gallery.service.interfaces.media.OpenPhotoService;

import java.util.List;

@Service
public class OpenPhotoServiceImpl implements OpenPhotoService {
    private final CategoryRepository categoryRepository;
    private final PhotoRepository photoRepository;
    private final MessageSource messageSource;

    @Autowired
    public OpenPhotoServiceImpl(CategoryRepository categoryRepository, PhotoRepository photoRepository, MessageSource messageSource) {
        this.categoryRepository = categoryRepository;
        this.photoRepository = photoRepository;
        this.messageSource = messageSource;
    }

    @Override
    public List<PhotoResponse> getPhotosOfCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException(messageSource.getMessage(
                        "category-not-found",
                        null,
                        "Category was not found",
                        LocaleContextHolder.getLocale()
                ))
        );

        return category.getPhotos().stream().map(PhotoResponse::new).toList();
    }

    @Override
    public PhotoResponse getPhotoById(Long id) {
        Photo photo = photoRepository.findById(id).orElseThrow(
                () -> new NotFoundException(messageSource.getMessage(
                        "photo-not-found",
                        null,
                        "Photo was not found",
                        LocaleContextHolder.getLocale()
                ))
        );

        return new PhotoResponse(photo);
    }
}
