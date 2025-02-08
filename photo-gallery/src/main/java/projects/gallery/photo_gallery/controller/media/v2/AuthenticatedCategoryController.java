package projects.gallery.photo_gallery.controller.media.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import projects.gallery.photo_gallery.dto.request.CategoryRequest;
import projects.gallery.photo_gallery.model.media.Category;
import projects.gallery.photo_gallery.service.interfaces.media.AuthenticatedCategoryService;

import java.net.URI;

@RestController
@RequestMapping("/api/media/auth/v2/categories")
public class AuthenticatedCategoryController {
    private final AuthenticatedCategoryService categoryService;
    private final MessageSource messageSource;

    @Autowired
    public AuthenticatedCategoryController(AuthenticatedCategoryService categoryService, MessageSource messageSource) {
        this.categoryService = categoryService;
        this.messageSource = messageSource;
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CategoryRequest dto) {
        Category category = categoryService.createCategory(dto);

        String message = messageSource.getMessage(
                "category-created",
                null,
                "Category was created",
                LocaleContextHolder.getLocale()
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();

        return ResponseEntity.created(location).body(message);
    }

    @DeleteMapping("/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return messageSource.getMessage(
                "category-deleted",
                null,
                "Category was deleted",
                LocaleContextHolder.getLocale()
        );
    }

    @PutMapping("/{categoryId}")
    public String patchCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody CategoryRequest dto
    ) {
        categoryService.patchCategory(dto, categoryId);
        return messageSource.getMessage(
                "category-patched",
                null,
                "Category was patched",
                LocaleContextHolder.getLocale()
        );
    }
}
