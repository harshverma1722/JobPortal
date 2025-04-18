package com.project.jobportal.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer { // This configuration class will map requests for /photos to serve files from a directory on our file system

    private static final String UPLOAD_DIR = "photos";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { // Override the default implementation to set up a custom resource handler
        exposeDirectory(UPLOAD_DIR,registry);
    }

    private void exposeDirectory(String uploadDir, ResourceHandlerRegistry registry) {  // Converts the uploadDir string to a path. It Maps requests starting with "/photos/**" to a file system location file:<absolute path to photos directory> . The ** will match on all sub-directories.
        Path path = Paths.get(uploadDir);
        registry.addResourceHandler("/" + uploadDir + "/**").addResourceLocations("file:" + path.toAbsolutePath() + "/");
    }
}
