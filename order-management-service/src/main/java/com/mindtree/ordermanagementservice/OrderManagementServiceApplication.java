package com.mindtree.ordermanagementservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class OrderManagementServiceApplication {

	static String FB_BASE_URL = "";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(OrderManagementServiceApplication.class, args);

		/*
		 * Task<FirebaseToken> decodedToken =
		 * FirebaseAuth.getInstance().verifyIdToken(args[0]+"");
		 * Thread.sleep(5000l);
		 * System.out.println("==decodedToken=="+decodedToken);
		 * System.out.println("===My Email=="+decodedToken.getResult().getEmail(
		 * ));
		 */

	}
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Order Management Service")
          //  .description("Awesome description")
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .termsOfServiceUrl("")
            .version("1.0.0")
            .build();
    }
	@Bean
	public Docket docket() {
		// Adding Header
		List<Parameter> parameters = new ArrayList<Parameter>();

		ParameterBuilder parameterBuilder = new ParameterBuilder();
		parameterBuilder.name("token").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
		parameters.add(parameterBuilder.build());
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.mindtree.ordermanagementservice.api"))

				.paths(PathSelectors.any()).build().apiInfo(apiInfo()).globalOperationParameters(parameters);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
