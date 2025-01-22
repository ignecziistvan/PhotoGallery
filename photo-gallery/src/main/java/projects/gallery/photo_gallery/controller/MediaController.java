package projects.gallery.photo_gallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projects.gallery.photo_gallery.dto.request.CategoryRequest;
import projects.gallery.photo_gallery.dto.response.CategoryResponse;
import projects.gallery.photo_gallery.dto.response.PhotoResponse;
import projects.gallery.photo_gallery.service.interfaces.MediaService;
import projects.gallery.photo_gallery.service.interfaces.PhotoUploadService;

import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {
    private final MediaService mediaService;
    private final PhotoUploadService photoUploadService;
    private final MessageSource messageSource;

    @Autowired
    public MediaController(MediaService mediaService, PhotoUploadService photoUploadService, MessageSource messageSource) {
        this.mediaService = mediaService;
        this.photoUploadService = photoUploadService;
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

    @PostMapping("/categories")
    public String createCategory(@RequestBody CategoryRequest dto) {
        mediaService.createCategory(dto);
        return messageSource.getMessage("category-created", null, "Category was created", LocaleContextHolder.getLocale());
    }

    @DeleteMapping("/categories/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") Long categoryId) {
        mediaService.deleteCategory(categoryId);
        return messageSource.getMessage("category-deleted", null, "Category was deleted", LocaleContextHolder.getLocale());
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
        photoUploadService.upload(categoryId, files);
        return messageSource.getMessage("photos-uploaded", null, "Photos had been uploaded", LocaleContextHolder.getLocale());
    }

    @DeleteMapping("/photos/{photoId}")
    public void deletePhoto(@PathVariable Long photoId) {
        photoUploadService.delete(photoId);
    }
}
