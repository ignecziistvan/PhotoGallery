package projects.gallery.photo_gallery.service.media.category;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import projects.gallery.photo_gallery.dto.request.CategoryRequest;
import projects.gallery.photo_gallery.exception.BadRequestException;
import projects.gallery.photo_gallery.exception.NotFoundException;
import projects.gallery.photo_gallery.model.media.Category;
import projects.gallery.photo_gallery.model.media.Photo;
import projects.gallery.photo_gallery.repository.media.CategoryRepository;
import projects.gallery.photo_gallery.repository.media.PhotoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticatedCategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private PhotoRepository photoRepository;
    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private AuthenticatedCategoryServiceImpl categoryService;


    @Test
    void testCreateCategory_ShouldSucceed() {
        //given
        CategoryRequest request = new CategoryRequest(
                "New Test Category",
                null,
                null
        );

        when(categoryRepository.save(any(Category.class))).thenReturn(
                new Category(request.getName())
        );

        //when
        Category savedCategory = categoryService.createCategory(request);

        //then
        assertNotNull(savedCategory);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testCreateCategory_ShouldThrowBadRequestExceptionForMissingName() {
        //given
        Category category = new Category("Test Category");
        CategoryRequest request = new CategoryRequest(
                "Test Category",
                "Something",
                1L
        );

        when(categoryRepository.findByAccessUrl("test-category")).thenReturn(Optional.of(category));

        //then
        assertThrows(
                BadRequestException.class,
                () -> categoryService.createCategory(request)
        );
        verify(categoryRepository, times(0)).save(any(Category.class));
    }

    @Test
    void testDeleteCategory_ShouldDeleteCategory() {
        //given
        Long id = 1L;
        Category category = new Category("Test Category");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        //when
        categoryService.deleteCategory(id);

        //then
        assertDoesNotThrow(() -> NotFoundException.class);
    }

    @Test
    void testDeleteCategory_ShouldThrowNotFoundException() {
        //given
        Long id = 1L;

        //then
        assertThrows(NotFoundException.class, () -> categoryService.deleteCategory(id));
    }

    @Test
    void testPatchCategory_NameAndDescriptionChanged_ShouldSucceed() {
        //given
        Long id = 1L;
        Category category = new Category("Test Category");

        CategoryRequest request = new CategoryRequest(
                "New Test Category",
                "",
                null
        );

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        //when
        categoryService.patchCategory(request, 1L);

        //then
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testPatchCategory_NoChanges_ShouldThrowBadRequestException() {
        //given
        Long id = 1L;
        Category category = new Category("Test Category");
        category.setDescription("Description");

        CategoryRequest request = new CategoryRequest(
                "Test Category",
                "Description",
                null
        );

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        //then
        assertThrows(BadRequestException.class, () -> {
            categoryService.patchCategory(request, id);
        });
        verify(categoryRepository, times(0)).save(any());
    }

    @Test
    void testPatchCategory_PhotoNotBelongToCategory_ShouldThrowBadRequestException() {
        //given
        Long id = 1L;
        Long photoId = 20L;
        Category category = new Category("Test Category");
        Photo currentThumbnailPhoto = new Photo(
                "testUrl",
                "testThumbUrl",
                "testCloudinaryUrl",
                category
        );
        currentThumbnailPhoto.setId(10L);
        category.setPhotos(List.of(currentThumbnailPhoto));

        Photo foundPhoto = new Photo(
                "testUrl2",
                "testThumbUrl2",
                "testCloudinaryUrl2",
                new Category("Another Category")
        );
        foundPhoto.setId(photoId);

        CategoryRequest request = new CategoryRequest(
                "Test Category",
                "",
                photoId
        );

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(photoRepository.findById(photoId)).thenReturn(Optional.of(foundPhoto));

        //then
        assertThrows(BadRequestException.class, () -> categoryService.patchCategory(request, id));
        verify(photoRepository, times(0)).save(any(Photo.class));
        verify(categoryRepository, times(0)).save(any(Category.class));
    }

    @Test
    void testPatchCategory_CategoryNotFound_ShouldThrowNotFoundException() {
        //given
        CategoryRequest request = new CategoryRequest(
                "Test Category",
                "",
                null
        );

        //then
        assertThrows(NotFoundException.class, () -> {
            categoryService.patchCategory(request, 1L);
        });
        verify(categoryRepository, times(0)).save(any());
    }
}