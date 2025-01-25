package projects.gallery.photo_gallery.service.media.photo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import projects.gallery.photo_gallery.dto.response.PhotoResponse;
import projects.gallery.photo_gallery.exception.NotFoundException;
import projects.gallery.photo_gallery.model.media.Category;
import projects.gallery.photo_gallery.model.media.Photo;
import projects.gallery.photo_gallery.repository.media.CategoryRepository;
import projects.gallery.photo_gallery.repository.media.PhotoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class OpenPhotoServiceImplTest {
    @Mock
    private PhotoRepository photoRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private OpenPhotoServiceImpl photoService;


    @Test
    void testGetPhotosOfCategory_ShouldReturnPhotosOfCategoryAsListOfPhotoResponse() {
        //given
        Category category = getCategory();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        //when
        List<PhotoResponse> list = photoService.getPhotosOfCategory(1L);

        //then
        assertEquals(2, list.size());
        assertEquals(category.getName(), list.get(0).getCategoryName());
        verify(categoryRepository, times(1)).findById(anyLong());
    }

    private static Category getCategory() {
        Category category = new Category("Test Category");
        Photo photo1 = new Photo(
                "testUrl",
                "testThumbnailUrl",
                "testCloudinaryPublicId",
                category
        );
        Photo photo2 = new Photo(
                "testUrl2",
                "testThumbnailUrl2",
                "testCloudinaryPublicId2",
                category
        );

        category.setPhotos(List.of(photo1, photo2));
        return category;
    }

    @Test
    void testGetPhotoById_ShouldReturnPhotoResponse() {
        //given
        Long id = 1L;
        Category category = new Category("Test Category");
        Photo photo = new Photo(
                "testUrl",
                "testThumbnailUrl",
                "testCloudinaryPublicId",
                category
        );

        when(photoRepository.findById(id)).thenReturn(Optional.of(photo));

        //when
        PhotoResponse photoResponse = photoService.getPhotoById(id);

        //then
        assertNotNull(photoResponse);
        verify(photoRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetPhotoById_ShouldThrowNotFoundException() {
        //given
        Long id = 1L;

        //then
        assertThrows(NotFoundException.class, () -> photoService.getPhotoById(id));
        verify(photoRepository, times(1)).findById(anyLong());
    }
}