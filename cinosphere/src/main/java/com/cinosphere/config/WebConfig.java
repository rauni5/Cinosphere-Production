package com.cinosphere.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Registers the ~/webassets directory as a static resource location so that
 * Spring Boot serves the uploaded images directly over HTTP — replacing all
 * five Get*Servlet classes (GetMoviePosterServlet, GetMovieBackgroundPosterServlet,
 * GetProfileImageServlet, GetIconServlet, GetLogoServlet).
 *
 * URL mapping:
 *   /uploads/movies/poster/**      → ~/webassets/poster/
 *   /uploads/movies/background/**  → ~/webassets/background/
 *   /uploads/profiles/**           → ~/webassets/profile/
 *   /uploads/icons/**              → ~/webassets/icon/
 *   /uploads/logos/**              → ~/webassets/logo/
 *
 * The Vite dev-server proxy (vite.config.js) forwards /uploads/* to Spring
 * Boot, so the React frontend uses the same paths in both dev and production.
 *
 * CORS is also configured here so the React dev server (localhost:5173) can
 * call the API without browser CORS errors. Tighten allowedOrigins before
 * deploying to production.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String ROOT = "file:" + FileStorageConfig.WEBASSETS_ROOT + "/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Movie posters   → GET /uploads/movies/poster/fileName.jpg
        registry.addResourceHandler("/uploads/movies/poster/**")
                .addResourceLocations(ROOT + "poster/");

        // Movie backgrounds → GET /uploads/movies/background/fileName.jpg
        registry.addResourceHandler("/uploads/movies/background/**")
                .addResourceLocations(ROOT + "background/");

        // User profile photos → GET /uploads/profiles/fileName.jpg
        registry.addResourceHandler("/uploads/profiles/**")
                .addResourceLocations(ROOT + "profile/");

        // Icons → GET /uploads/icons/fileName.png
        registry.addResourceHandler("/uploads/icons/**")
                .addResourceLocations(ROOT + "icon/");

        // Logos → GET /uploads/logos/fileName.png
        registry.addResourceHandler("/uploads/logos/**")
                .addResourceLocations(ROOT + "logo/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                // React dev server — change to your production domain before deploying
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}