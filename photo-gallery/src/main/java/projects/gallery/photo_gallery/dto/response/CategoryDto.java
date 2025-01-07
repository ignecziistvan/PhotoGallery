package projects.gallery.photo_gallery.dto.response;

import lombok.Getter;
import projects.gallery.photo_gallery.model.Category;

@Getter
public class CategoryDto {
    private final Long id;
    private final String name;
    private final String accessUrl;
    private final String description;
    private final String thumbnailUrl;

    public CategoryDto(Category c) {
        this.id = c.getId();
        this.name = c.getName();
        this.accessUrl = c.getAccessUrl();
        this.description = c.getDescription();

        if (c.getThumbnailPhoto() != null) {
            this.thumbnailUrl = c.getThumbnailPhoto().getUrl();
        } else {
            this.thumbnailUrl = null;
        }
    }
}
