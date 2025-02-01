package projects.gallery.photo_gallery.controller.media.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projects.gallery.photo_gallery.dto.response.PhotoResponse;
import projects.gallery.photo_gallery.service.interfaces.media.OpenPhotoService;

import java.util.List;

@RestController
@RequestMapping("/api/media/open/v2/photos")
public class OpenPhotoController {
    private final OpenPhotoService photoService;

    @Autowired
    public OpenPhotoController(OpenPhotoService photoService) {
        this.photoService = photoService;
    }


    @GetMapping
    public List<PhotoResponse> getAllPhotosOfCategory(@RequestParam Long categoryId) {
        return photoService.getPhotosOfCategory(categoryId);
    }

    @GetMapping("/{photoId}")
    public PhotoResponse getPhoto(@PathVariable Long photoId) {
        return photoService.getPhotoById(photoId);
    }
}
