package projects.gallery.photo_gallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projects.gallery.photo_gallery.exception.BadRequestException;
import projects.gallery.photo_gallery.exception.NotFoundException;
import projects.gallery.photo_gallery.model.Category;
import projects.gallery.photo_gallery.model.Photo;
import projects.gallery.photo_gallery.repository.CategoryRepository;
import projects.gallery.photo_gallery.service.interfaces.PhotoUploadService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class LocalPhotoUploadService implements PhotoUploadService {
    @Autowired
    private CategoryRepository categoryRepository;
    private static final List<String> SUPPORTED_IMAGE_TYPES = List.of("jpeg", "jpg", "png", "webp");
    private final String url = "http://localhost:8080/categories/";


    @Override
    public void upload(Long categoryId, MultipartFile[] files) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException(
                        "Category was not found"
                )
        );

        Arrays.stream(files).map(this::validateFileType);

        for (MultipartFile file : files) {
            try {
                String directoryOfFullImage = "src/main/resources/static/categories/" + categoryId + "/images/";
                String directoryOfThumbnailImage = "src/main/resources/static/categories/" + categoryId + "/thumbnails/";
                String uniqueFileName = renameFileToRandomUUID(file.getOriginalFilename());

                Files.createDirectories(Path.of(directoryOfFullImage));
                Files.createDirectories(Path.of(directoryOfThumbnailImage));

                File fullImgFile = new File(directoryOfFullImage + uniqueFileName);
                File thumbnailImgFile = new File(directoryOfThumbnailImage + uniqueFileName);

                Files.copy(file.getInputStream(), fullImgFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(file.getInputStream(), thumbnailImgFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                Photo photo = new Photo(
                        url + categoryId + "/images/" + uniqueFileName,
                        url + categoryId + "/thumbnails/" + uniqueFileName,
                        category
                );

                category.getPhotos().add(photo);
                categoryRepository.save(category);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Long photoId) {

    }

    private boolean validateFileType(MultipartFile file) {
        String contentType = file.getContentType();
        if (
                contentType == null ||
                        !contentType.startsWith("image/")
        ) {
            throw new BadRequestException(
                    "Unsupported or invalid media type: " + contentType
            );
        }

        boolean isSupportedMediaType = SUPPORTED_IMAGE_TYPES.stream().anyMatch(contentType::endsWith);
        if (!isSupportedMediaType) {
            throw new BadRequestException(
                    "Unsupported image type: " + contentType
            );
        }

        return true;
    }

    private String renameFileToRandomUUID(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }

        int extensionIndex = fileName.lastIndexOf(".");
        String extension = fileName.substring(extensionIndex);

        return UUID.randomUUID().toString() + extension;
    }
}
