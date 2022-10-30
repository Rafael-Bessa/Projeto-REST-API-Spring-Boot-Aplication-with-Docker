/**
 * localhost:8080/swagger-ui.html
 */

package bessa.morangon.rafael.challenge.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bessa.morangon.rafael.challenge.model.Usuario;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	
	@Bean
	public Docket api() {
	    return new Docket(DocumentationType.SWAGGER_2)
	        .select()
	        .apis(RequestHandlerSelectors.basePackage("bessa.morangon.rafael.challenge"))
	        .paths(PathSelectors.ant("/**"))
	        .build()
	        .ignoredParameterTypes(Usuario.class)
	        .globalOperationParameters(Arrays.asList(
                    new ParameterBuilder()
                            .name("Authorization")
                            .description("Header para token JWT")
                            .modelRef(new ModelRef("string"))
                            .parameterType("header")
                            .required(false)
                            .build()));
	}
	
}
