package projects.gallery.photo_gallery.service.media;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projects.gallery.photo_gallery.exception.BadRequestException;
import projects.gallery.photo_gallery.exception.NotFoundException;
import projects.gallery.photo_gallery.model.media.Category;
import projects.gallery.photo_gallery.model.media.Photo;
import projects.gallery.photo_gallery.repository.media.CategoryRepository;
import projects.gallery.photo_gallery.service.interfaces.PhotoUploadService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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

        List<String> invalidFileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                validateFileType(file);
            } catch (BadRequestException e) {
                invalidFileNames.add(file.getOriginalFilename());
            }
        }
        if (!invalidFileNames.isEmpty() && files.length > 1) {
            throw new BadRequestException("Unsupported files: " + String.join(", ", invalidFileNames));
        }

        for (MultipartFile file : files) {
            try {
                if (!validateFileType(file)) continue;
                String directoryOfFullImage = "src/main/resources/static/categories/" + categoryId + "/images/";
                String directoryOfThumbnailImage = "src/main/resources/static/categories/" + categoryId + "/thumbnails/";
                String uniqueFileName = renameFileToRandomUUID(file.getOriginalFilename());

                Files.createDirectories(Path.of(directoryOfFullImage));
                Files.createDirectories(Path.of(directoryOfThumbnailImage));

                File fullImgFile = new File(directoryOfFullImage + uniqueFileName);
                File thumbnailImgFile = new File(directoryOfThumbnailImage + uniqueFileName);

                Path thumbnail = createThumbnail(file);

                Files.copy(file.getInputStream(), fullImgFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(thumbnail, thumbnailImgFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                Photo photo = new Photo(
                        url + categoryId + "/images/" + uniqueFileName,
                        url + categoryId + "/thumbnails/" + uniqueFileName,
                        null,
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

    private Path createThumbnail(MultipartFile file) throws IOException {
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) {
            throw new IOException("Failed to read file as an image");
        }

        Path thumbnailTempFile = Files.createTempFile("thumbnail-", renameFileToRandomUUID(file.getOriginalFilename()));
        Files.copy(file.getInputStream(), thumbnailTempFile, StandardCopyOption.REPLACE_EXISTING);

        Thumbnails.of(image)
                .size(500, 500)
                .crop(Positions.CENTER)
                .toFile(thumbnailTempFile.toFile());

        return thumbnailTempFile;
    }
}
