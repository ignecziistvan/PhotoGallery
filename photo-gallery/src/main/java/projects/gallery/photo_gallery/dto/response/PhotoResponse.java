package projects.gallery.photo_gallery.dto.response;

import lombok.Getter;
import projects.gallery.photo_gallery.model.media.Photo;

@Getter
public class PhotoResponse {
    private final Long id;
    private final String url;
    private final String thumbnailUrl;
    private final Long categoryId;
    private final String categoryName;

    public PhotoResponse(Photo p) {
        this.id = p.getId();
        this.url = p.getUrl();
        this.thumbnailUrl = p.getThumbnailUrl();
        this.categoryId = p.getCategory().getId();
        this.categoryName = p.getCategory().getName();
    }
}
