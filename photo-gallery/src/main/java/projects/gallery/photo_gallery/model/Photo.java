package projects.gallery.photo_gallery.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String thumbnailUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public Photo(String url, String thumbnailUrl, Category category) {
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.category = category;
    }
}
