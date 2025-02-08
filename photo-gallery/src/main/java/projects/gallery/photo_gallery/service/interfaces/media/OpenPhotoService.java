package projects.gallery.photo_gallery.service.interfaces.media;

import projects.gallery.photo_gallery.dto.response.PhotoResponse;

import java.util.List;

public interface OpenPhotoService {
    List<PhotoResponse> getPhotosOfCategory(Long categoryId);
    PhotoResponse getPhotoById(Long id);
}
