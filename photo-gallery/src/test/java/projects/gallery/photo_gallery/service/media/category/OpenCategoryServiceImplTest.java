package projects.gallery.photo_gallery.service.media.category;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import projects.gallery.photo_gallery.dto.response.CategoryResponse;
import projects.gallery.photo_gallery.exception.NotFoundException;
import projects.gallery.photo_gallery.model.media.Category;
import projects.gallery.photo_gallery.model.media.Photo;
import projects.gallery.photo_gallery.repository.media.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class OpenCategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private OpenCategoryServiceImpl categoryService;


    @Test
    void testGetCategoryById_ShouldReturnCategoryResponse() {
        //given
        Long id = 1L;
        Category category = new Category("Test Category");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        //when
        CategoryResponse response = categoryService.getCategoryById(id);

        //then
        assertNotNull(response);
        verify(categoryRepository, times(1)).findById(Mockito.anyLong());
    }

    @Test
    void testGetCategoryById_ShouldThrowNotFoundException() {
        //given
        Long id = 1L;

        //then
        assertThrows(NotFoundException.class, () -> categoryService.getCategoryById(id));
        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetAllCategories_ShouldReturnAllCategoriesAsCategoryResponse() {
        //given
        Category category1 = new Category("Test Category 1");
        category1.setThumbnailPhoto(new Photo(
                "testUrl",
                "testThumbnailUrl",
                "testCloudinaryPublicUrl",
                category1
        ));
        Category category2 = new Category("Test Category 2");
        category2.setThumbnailPhoto(new Photo(
                "testUrl2",
                "testThumbnailUrl2",
                "testCloudinaryPublicUrl2",
                category2
        ));

        List<Category> categories = List.of(category1, category2);

        when(categoryRepository.findAll()).thenReturn(categories);

        //when
        List<CategoryResponse> foundCategories = categoryService.getAllCategories();

        //then
        assertNotNull(foundCategories);
        assertEquals(2, foundCategories.size());
        assertEquals(
                category1.getThumbnailPhoto().getThumbnailUrl(),
                foundCategories.get(0).getThumbnailUrl()
        );
        assertEquals(
                category2.getThumbnailPhoto().getThumbnailUrl(),
                foundCategories.get(1).getThumbnailUrl()
        );
        verify(categoryRepository, times(1)).findAll();
    }
}