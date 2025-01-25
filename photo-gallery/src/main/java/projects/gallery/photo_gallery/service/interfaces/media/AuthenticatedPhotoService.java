package projects.gallery.photo_gallery.service.interfaces.media;

import org.springframework.web.multipart.MultipartFile;

public interface AuthenticatedPhotoService {
    void upload(Long categoryId, MultipartFile[] files);
    void delete(Long photoId);
}
