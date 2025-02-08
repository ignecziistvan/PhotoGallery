package projects.gallery.photo_gallery.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryRequest {
    @NotNull(message = "{form-error.not-null}")
    @NotBlank(message = "{form-error.category.name.not-blank}")
    @Size(
            max = 30,
            message = "{form-error.category.name.size}"
    )
    private String name;

    @NotNull(message = "{form-error.category.description.not-null}")
    @Size(
            max = 1000,
            message = "{form-error.category.description.size}"
    )
    private String description;

    private Long thumbnailPhotoId;
}
