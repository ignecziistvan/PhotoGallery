package projects.gallery.photo_gallery.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(columnNames = "role")
})
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;

    public Role(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }
}
