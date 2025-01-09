package projects.gallery.photo_gallery.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoUploadService {
    void upload(Long categoryId, MultipartFile[] files);
    void delete(Long photoId);
}
