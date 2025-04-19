package edu.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Config class for setting up CORS policies.
 * Contains bean definition and settings.
 *
 * AnnotationConfiguration signals to Spring that the class contains
 * bean definition and config settings to be processed by the Spring container
 * during application startup.
 */
@Configuration
public class WebConfig {

  /**
   * Bean annotated class, i.e. objects returned should be managed by
   * the Spring container as a bean.
   * WebMvcConfigurer is an interface for Spring MVC configuration.
   * addCorsMappings uses override to customize the CORS settings.
   * Takes CorsRegistry object as parameter to define CORS policies.
   *
   * registry.addMapping("/api/**")
   * - Specifies where CORS policies applies.
   * - Here: any sub-path under /api
   *
   * .allowedOrigins("http://localhost:5173")
   * - Specifies origins that are allowed access.
   *
   * .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
   * - Specifies HTTP methods allowed access.
   * - OPTIONS allows handling of pre-flight requests from the browser as part of the CORS protocol.
   *
   * .allowedHeaders("*")
   * - Defines which headers can be included in the request.
   * - Using a wildcard allows all headers.
   *
   * .allowCredentials(true);
   * - Allows cookies and other credentials to be included in the request.
   * - Useful for requests that require authentication.
   *
   * @return WebMvcConfigurer
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:5173")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
      }
    };
  }
}
