package projects.gallery.photo_gallery.controller.media.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.gallery.photo_gallery.dto.response.CategoryResponse;
import projects.gallery.photo_gallery.service.interfaces.media.OpenCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/media/open/v2/categories")
public class OpenCategoryController {
    private final OpenCategoryService categoryService;

    @Autowired
    public OpenCategoryController(OpenCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public CategoryResponse getCategory(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }
}
