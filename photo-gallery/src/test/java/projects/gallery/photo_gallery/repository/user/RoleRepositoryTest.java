package projects.gallery.photo_gallery.repository.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import projects.gallery.photo_gallery.model.user.Role;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class RoleRepositoryTest {
    @Autowired
    private RoleRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(new Role("ROLE_USER"));
        repository.save(new Role("ROLE_ADMIN"));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void testFindByRole_ShouldFindUserRoleNadAdminRole() {
        //when
        Optional<Role> userRole = repository.findByRole("ROLE_USER");
        Optional<Role> adminRole = repository.findByRole("ROLE_ADMIN");

        //then
        assertTrue(userRole.isPresent());
        assertTrue(adminRole.isPresent());
    }

    @Test
    void testFindByRole_ShouldNotFindAnyRole() {
        //when
        Optional<Role> userRole1 = repository.findByRole("ROLEUSER");
        Optional<Role> userRole2 = repository.findByRole("ROLE USER");
        Optional<Role> userRole3 = repository.findByRole("role_user");
        Optional<Role> adminRole1 = repository.findByRole("ROLEADMIN");
        Optional<Role> adminRole2 = repository.findByRole("ROLE ADMIN");
        Optional<Role> adminRole3 = repository.findByRole("role_admin");

        //then
        assertFalse(userRole1.isPresent());
        assertFalse(userRole2.isPresent());
        assertFalse(userRole3.isPresent());
        assertFalse(adminRole1.isPresent());
        assertFalse(adminRole2.isPresent());
        assertFalse(adminRole3.isPresent());
    }
}