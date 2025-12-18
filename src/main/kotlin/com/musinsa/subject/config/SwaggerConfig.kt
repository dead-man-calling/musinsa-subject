package com.musinsa.subject.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class SwaggerConfig : WebMvcConfigurer {

    companion object {
        private const val API_NAME = "Product API"
        private const val API_VERSION = "1.0.0"
        private const val API_DESCRIPTION = "상품 API"
        private const val ROOT_PATH = "/"
        private const val SWAGGER_PATH = "/swagger-ui/index.html"
    }

    @Bean
    fun openAPI(): OpenAPI {
        val info = Info()
            .title(API_NAME)
            .version(API_VERSION)
            .description(API_DESCRIPTION)

        return OpenAPI()
            .components(Components())
            .info(info)
    }

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addRedirectViewController(ROOT_PATH, SWAGGER_PATH)
    }

}
