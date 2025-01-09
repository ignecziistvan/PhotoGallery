package projects.gallery.photo_gallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projects.gallery.photo_gallery.dto.response.CategoryDto;
import projects.gallery.photo_gallery.dto.response.PhotoDto;
import projects.gallery.photo_gallery.service.MediaService;

import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        return mediaService.getAllCategories();
    }

    @GetMapping("/categories/{categoryId}")
    public CategoryDto getCategory(@PathVariable Long categoryId) {
        return mediaService.getCategoryById(categoryId);
    }

    @GetMapping("/categories/{categoryId}/photos")
    public List<PhotoDto> getAllPhotos(@PathVariable Long categoryId) {
        return mediaService.getPhotosOfCategory(categoryId);
    }

    @GetMapping("/photos/{photoId}")
    public PhotoDto getPhoto(@PathVariable Long photoId) {
        return mediaService.getPhotoById(photoId);
    }
}
