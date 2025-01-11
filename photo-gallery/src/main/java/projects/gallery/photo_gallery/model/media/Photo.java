package projects.gallery.photo_gallery.model.media;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "photos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "url"})
})
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String thumbnailUrl;

    private String cloudinaryPublicId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public Photo(String url, String thumbnailUrl, String cloudinaryPublicId, Category category) {
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.cloudinaryPublicId = cloudinaryPublicId;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(id, photo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
