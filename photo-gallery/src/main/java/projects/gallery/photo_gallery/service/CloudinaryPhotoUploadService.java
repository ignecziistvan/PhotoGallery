package projects.gallery.photo_gallery.service;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projects.gallery.photo_gallery.exception.BadRequestException;
import projects.gallery.photo_gallery.exception.NotFoundException;
import projects.gallery.photo_gallery.model.Category;
import projects.gallery.photo_gallery.model.Photo;
import projects.gallery.photo_gallery.repository.CategoryRepository;
import projects.gallery.photo_gallery.repository.PhotoRepository;
import projects.gallery.photo_gallery.service.interfaces.PhotoUploadService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Primary
public class CloudinaryPhotoUploadService implements PhotoUploadService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private Cloudinary cloudinary;
    private static final List<String> SUPPORTED_IMAGE_TYPES = List.of("jpeg", "jpg", "png", "webp");

    @Override
    public void upload(Long categoryId, MultipartFile[] files) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException(
                        "Category was not found"
                )
        );

        List<String> invalidFileNames = new ArrayList<>();
        System.out.println(Arrays.toString(files));
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
                validateFileType(file);
                String imgFolderPath = "PhotoGallery/" + categoryId;

                Map result = cloudinary.uploader().upload(
                        file.getBytes(),
                        Map.of(
                                "folder", imgFolderPath,
                                "resource_type", "image"
                        )
                );

                System.out.println(result);

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
                        "Photo was not found"
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

    private String createThumbnailUrl(String imageUrl) {
        return imageUrl.replace("upload/", "upload/w_300,h_300,c_fill/");
    }
}
