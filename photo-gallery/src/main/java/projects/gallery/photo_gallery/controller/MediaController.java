package projects.gallery.photo_gallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private MediaService mediaService;
    @Autowired
    private PhotoUploadService photoUploadService;


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
        return "Category created successfully";
    }

    @DeleteMapping("/categories/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") Long categoryId) {
        mediaService.deleteCategory(categoryId);
        return "Category deleted";
    }

    @PutMapping("/categories/{categoryId}")
    public String patchCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody CategoryRequest dto
    ) {
        mediaService.patchCategory(dto, categoryId);
        return "Category patched successfully";
    }



    // --- Photos ---

    @PostMapping("/categories/{categoryId}/upload")
    public String uploadPhotos(@PathVariable Long categoryId, @RequestParam("files[]") MultipartFile[] files) {
        photoUploadService.upload(categoryId, files);
        return "Successfully uploaded photos";
    }

    @DeleteMapping("/photos/{photoId}")
    public void deletePhoto(@PathVariable Long photoId) {
        photoUploadService.delete(photoId);
    }
}
