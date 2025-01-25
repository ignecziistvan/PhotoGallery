package projects.gallery.photo_gallery.repository.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import projects.gallery.photo_gallery.model.user.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(new User(
            "testUsername",
            "test@user.com",
            "testPassword",
            "Test",
            "User"
        ));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void testFindByUsername_ShouldFindUser() {
        //when
        Optional<User> foundUser = repository.findByUsername("testUsername");

        //then
        assertTrue(foundUser.isPresent());
    }

    @Test
    void testFindByUsername_ShouldNotFindUser() {
        //when
        Optional<User> notFoundUser1 = repository.findByUsername("testusername");
        Optional<User> notFoundUser2 = repository.findByUsername("test username");

        //then
        assertFalse(notFoundUser1.isPresent());
        assertFalse(notFoundUser2.isPresent());
    }

    @Test
    void testFindByEmail_ShouldFindUser() {
        //when
        Optional<User> foundUser = repository.findByEmail("test@user.com");

        //then
        assertTrue(foundUser.isPresent());
    }

    @Test
    void testFindByEmail_ShouldNotFindUser() {
        //when
        Optional<User> notFoundUser1 = repository.findByEmail("test@gmail.com");
        Optional<User> notFoundUser2 = repository.findByUsername("test@user.co");

        //then
        assertFalse(notFoundUser1.isPresent());
        assertFalse(notFoundUser2.isPresent());
    }
}