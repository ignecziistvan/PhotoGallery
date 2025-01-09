package projects.gallery.photo_gallery.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Map;

@Configuration
public class CloudinaryConfiguration {
    @Autowired
    private Environment env;

    @Bean
    Cloudinary cloudinary() {
        return new Cloudinary(
                Map.of(
                        "cloud_name", env.getProperty("cloudinary.cloud-name"),
                        "api_key", env.getProperty("cloudinary.api-key"),
                        "api_secret", env.getProperty("cloudinary.api-secret")
                )
        );
    }
}
