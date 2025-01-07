package projects.gallery.photo_gallery.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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
    private Photo thumbnail;

    public Category(String name, String accessUrl, List<Photo> photos) {
        this.name = name;
        this.accessUrl = accessUrl;
        this.photos = photos;
    }
}
