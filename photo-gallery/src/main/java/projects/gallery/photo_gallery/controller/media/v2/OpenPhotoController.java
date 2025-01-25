package projects.gallery.photo_gallery.controller.media.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


    @GetMapping("/{categoryId}")
    public List<PhotoResponse> getAllPhotosOfCategory(@PathVariable Long categoryId) {
        return photoService.getPhotosOfCategory(categoryId);
    }

    @GetMapping("/{photoId}")
    public PhotoResponse getPhoto(@PathVariable Long photoId) {
        return photoService.getPhotoById(photoId);
    }
}
