package projects.gallery.photo_gallery.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://photo-gallery-frontend.s3-website.eu-north-1.amazonaws.com", "http://localhost:5173")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*");
    }
}
