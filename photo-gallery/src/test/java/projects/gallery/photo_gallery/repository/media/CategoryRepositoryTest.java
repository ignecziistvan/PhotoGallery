package projects.gallery.photo_gallery.repository.media;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import projects.gallery.photo_gallery.model.media.Category;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(new Category("Test Category"));
        repository.save(new Category("Another Test Category"));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void testFindByAccessUrl_ShouldFindÍCategory() {
        //when
        Optional<Category> foundCategory1 = repository.findByAccessUrl("test-category");
        Optional<Category> foundCategory2 = repository.findByAccessUrl("another-test-category");

        //then
        assertTrue(foundCategory1.isPresent());
        assertTrue(foundCategory2.isPresent());
    }

    @Test
    void testFindByAccessUrl_ShouldNotFindÍCategory() {
        //when
        Optional<Category> notFoundCauseOfIncorrectAccessUrl1 = repository.findByAccessUrl("test-Category");
        Optional<Category> notFoundCauseOfIncorrectAccessUrl2 = repository.findByAccessUrl("test category");
        Optional<Category> notFoundCategory1 = repository.findByAccessUrl("random-text");

        //then
        assertFalse(notFoundCauseOfIncorrectAccessUrl1.isPresent());
        assertFalse(notFoundCauseOfIncorrectAccessUrl2.isPresent());
        assertFalse(notFoundCategory1.isPresent());
    }
}