package projects.gallery.photo_gallery.controller.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import projects.gallery.photo_gallery.dto.request.CategoryRequest;
import projects.gallery.photo_gallery.dto.response.CategoryResponse;
import projects.gallery.photo_gallery.dto.response.PhotoResponse;
import projects.gallery.photo_gallery.model.media.Category;
import projects.gallery.photo_gallery.service.interfaces.media.MediaService;
import projects.gallery.photo_gallery.service.interfaces.media.AuthenticatedPhotoService;

import java.net.URI;
import java.util.List;

@Deprecated
@RestController
@RequestMapping("/api/media")
public class MediaController {
    private final MediaService mediaService;
    private final AuthenticatedPhotoService authenticatedPhotoService;
    private final MessageSource messageSource;

    @Autowired
    public MediaController(MediaService mediaService, AuthenticatedPhotoService authenticatedPhotoService, MessageSource messageSource) {
        this.mediaService = mediaService;
        this.authenticatedPhotoService = authenticatedPhotoService;
        this.messageSource = messageSource;
    }

    @GetMapping("/categories")
    public List<CategoryResponse> getAllCategories() {
        return mediaService.getAllCategories();
    }

    @GetMapping("/categories/{categoryId}")
    public CategoryResponse getCategory(@PathVariable Long categoryId) {
        return mediaService.getCategoryById(categoryId);
    }

    @GetMapping("/categories/{categoryId}/photos")
    public List<PhotoResponse> getAllPhotos(@PathVariable Long categoryId) {
        return mediaService.getPhotosOfCategory(categoryId);
    }

    @GetMapping("/photos/{photoId}")
    public PhotoResponse getPhoto(@PathVariable Long photoId) {
        return mediaService.getPhotoById(photoId);
    }



    // -------- Authenticated endpoints --------

    // --- Categories ---

    @Deprecated
    @PostMapping("/categories")
    public ResponseEntity<String> createCategory(@RequestBody CategoryRequest dto) {
        Category category = mediaService.createCategory(dto);

        String message = messageSource.getMessage(
                "category-created",
                null,
                "Category was created",
                LocaleContextHolder.getLocale()
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();

        return ResponseEntity.created(location).body(message);
    }

    @DeleteMapping("/categories/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") Long categoryId) {
        mediaService.deleteCategory(categoryId);
        return messageSource.getMessage(
                "category-deleted",
                null,
                "Category was deleted",
                LocaleContextHolder.getLocale()
        );
    }

    @PutMapping("/categories/{categoryId}")
    public String patchCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody CategoryRequest dto
    ) {
        mediaService.patchCategory(dto, categoryId);
        return messageSource.getMessage("category-patched", null, "Category was patched", LocaleContextHolder.getLocale());
    }



    // --- Photos ---

    @PostMapping("/categories/{categoryId}/upload")
    public String uploadPhotos(@PathVariable Long categoryId, @RequestParam("files[]") MultipartFile[] files) {
        authenticatedPhotoService.upload(categoryId, files);
        return messageSource.getMessage("photos-uploaded", null, "Photos had been uploaded", LocaleContextHolder.getLocale());
    }

    @DeleteMapping("/photos/{photoId}")
    public void deletePhoto(@PathVariable Long photoId) {
        authenticatedPhotoService.delete(photoId);
    }
}
