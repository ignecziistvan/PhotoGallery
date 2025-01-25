package projects.gallery.photo_gallery.controller.media.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projects.gallery.photo_gallery.service.interfaces.media.AuthenticatedPhotoService;

@RestController
@RequestMapping("/api/media/auth/v2/photos")
public class AuthenticatedPhotoController {
    private final AuthenticatedPhotoService photoService;
    private final MessageSource messageSource;

    @Autowired
    public AuthenticatedPhotoController(AuthenticatedPhotoService photoService, MessageSource messageSource) {
        this.photoService = photoService;
        this.messageSource = messageSource;
    }

    @PostMapping("/upload")
    public String uploadPhotos(
            @RequestParam Long categoryId,
            @RequestParam("files[]") MultipartFile[] files
    ) {
        photoService.upload(categoryId, files);
        return messageSource.getMessage(
                "photos-uploaded",
                null,
                "Photos had been uploaded",
                LocaleContextHolder.getLocale()
        );
    }

    @DeleteMapping("/{photoId}")
    public void deletePhoto(@PathVariable Long photoId) {
        photoService.delete(photoId);
    }
}
