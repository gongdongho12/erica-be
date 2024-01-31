package erica.be.configure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {
    @Bean
    fun swaggerApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(swaggerInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("erica.be"))
            .paths(PathSelectors.any())
            .build()
            .useDefaultResponseMessages(false)
    }

    private fun swaggerInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("Erica API Documentation")
            .description("Erica 서버 API 문서입니다.")
            .version("1.0.0")
            .build()
    }
}
