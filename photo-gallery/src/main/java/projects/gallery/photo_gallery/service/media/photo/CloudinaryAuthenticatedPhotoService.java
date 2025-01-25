package projects.gallery.photo_gallery.service.media.photo;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projects.gallery.photo_gallery.exception.BadRequestException;
import projects.gallery.photo_gallery.exception.NotFoundException;
import projects.gallery.photo_gallery.model.media.Category;
import projects.gallery.photo_gallery.model.media.Photo;
import projects.gallery.photo_gallery.repository.media.CategoryRepository;
import projects.gallery.photo_gallery.repository.media.PhotoRepository;
import projects.gallery.photo_gallery.service.interfaces.media.AuthenticatedPhotoService;

import java.io.IOException;
import java.util.*;

@Service
@Primary
public class CloudinaryAuthenticatedPhotoService implements AuthenticatedPhotoService {
    private final CategoryRepository categoryRepository;
    private final PhotoRepository photoRepository;
    private final Cloudinary cloudinary;
    private final MessageSource messageSource;
    private static final List<String> SUPPORTED_IMAGE_TYPES = List.of("jpeg", "jpg", "png", "webp");

    @Autowired
    public CloudinaryAuthenticatedPhotoService(CategoryRepository categoryRepository, PhotoRepository photoRepository, Cloudinary cloudinary, MessageSource messageSource) {
        this.categoryRepository = categoryRepository;
        this.photoRepository = photoRepository;
        this.cloudinary = cloudinary;
        this.messageSource = messageSource;
    }

    @Override
    public void upload(Long categoryId, MultipartFile[] files) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException(
                        messageSource.getMessage("category-not-found", null, "Category was not found", LocaleContextHolder.getLocale())
                )
        );

        List<String> invalidFileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                validateFileType(file);
            } catch (BadRequestException e) {
                invalidFileNames.add(file.getOriginalFilename());
            }
        }
        if (!invalidFileNames.isEmpty() && files.length > 1) {
            throw new BadRequestException(
                    messageSource.getMessage("unsupported-files", null, "Unsupported files", LocaleContextHolder.getLocale()) +
                            ": " +
                            String.join(", ", invalidFileNames));
        }

        for (MultipartFile file : files) {
            try {
                validateFileType(file);
                String imgFolderPath = "PhotoGallery/" + categoryId;

                Map result = cloudinary.uploader().upload(
                        file.getBytes(),
                        Map.of(
                                "folder", imgFolderPath,
                                "resource_type", "image"
                        )
                );

                String imageUrl = result.get("secure_url").toString();
                String thumbnailUrl = createThumbnailUrl(imageUrl);
                String publicId = result.get("public_id").toString();

                Photo photo = new Photo(
                        imageUrl,
                        thumbnailUrl,
                        publicId,
                        category
                );

                category.getPhotos().add(photo);
                categoryRepository.save(category);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Long photoId) {
        Photo photo = photoRepository.findById(photoId).orElseThrow(
                () -> new NotFoundException(
                        messageSource.getMessage("photo-not-found", null, "Photo was not found", LocaleContextHolder.getLocale())
                )
        );


        try {
            if (photo.getCloudinaryPublicId() != null) {
                String publicId = photo.getCloudinaryPublicId();
                cloudinary.uploader().destroy(publicId, null);
            }
            photoRepository.delete(photo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateFileType(MultipartFile file) {
        String contentType = file.getContentType();
        if (
                contentType == null ||
                        !contentType.startsWith("image/")
        ) {
            throw new BadRequestException(
                    messageSource.getMessage(
                            "unsupported-or-invalid-media-type",
                            null,
                            "Unsupported or invalid media type",
                            LocaleContextHolder.getLocale()
                    ) + ": " + contentType
            );
        }

        boolean isSupportedMediaType = SUPPORTED_IMAGE_TYPES.stream().anyMatch(contentType::endsWith);
        if (!isSupportedMediaType) {
            throw new BadRequestException(
                    messageSource.getMessage(
                            "unsupported-image-type",
                            null,
                            "Unsupported image type",
                            LocaleContextHolder.getLocale()
                    ) + ": " + contentType
            );
        }

        return true;
    }

    private String createThumbnailUrl(String imageUrl) {
        return imageUrl.replace("upload/", "upload/w_300,h_300,c_fill/");
    }
}
