package projects.gallery.photo_gallery.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.text.Normalizer;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "categories")
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
}
