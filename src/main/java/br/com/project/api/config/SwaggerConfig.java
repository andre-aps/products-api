package br.com.project.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("br.com.project.api.controller"))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(metaInfo());
    }
	
	private ApiInfo metaInfo() {
		return new ApiInfoBuilder()
                .title("Products API Documentation")
                .description("This pages documents Products API RESTful endpoints")
                .version("1.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("André Pereira da Silva", "https://github.com/andre-aps", "andre.gmaps@gmail.com"))
                .build();
    }
}
