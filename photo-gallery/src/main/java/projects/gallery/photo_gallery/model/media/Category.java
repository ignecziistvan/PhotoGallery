package projects.gallery.photo_gallery.model.media;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.text.Normalizer;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "name", "access_url"})
})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String accessUrl;
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos;

    @OneToOne(fetch = FetchType.LAZY)
    private Photo thumbnailPhoto;

    public Category(String name) {
        this.name = name;
        this.accessUrl = generateAccessUrl(name);
    }

    private String generateAccessUrl(String categoryName) {
        return Normalizer.normalize(categoryName, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replace(" ", "-")
                .toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
