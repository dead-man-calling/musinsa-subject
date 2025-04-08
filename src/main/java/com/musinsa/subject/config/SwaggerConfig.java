package com.musinsa.subject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    private static final String API_NAME = "Product API";
    private static final String API_VERSION = "1.0.0";
    private static final String API_DESCRIPTION = "상품 API";
    private static final String ROOT_PATH = "/";
    private static final String SWAGGER_PATH = "/swagger-ui/index.html";

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION);

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController(ROOT_PATH, SWAGGER_PATH);
    }

}
