package com.mindtree.ordermanagementservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

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

	@Bean
	public Docket docket() {
		// Adding Header
		List<Parameter> aParameters = new ArrayList<Parameter>();
		ParameterBuilder aParameterBuilder1 = new ParameterBuilder();
		// aParameterBuilder1.name("userInfoUri").modelRef(new
		// ModelRef("string")).parameterType("header").required(true)
		// .build();
		// ParameterBuilder aParameterBuilder2 = new ParameterBuilder();
		// aParameterBuilder2.name("tokenValidateUri").modelRef(new
		// ModelRef("string")).parameterType("header")
		// .required(true).build();
		ParameterBuilder aParameterBuilder3 = new ParameterBuilder();
		aParameterBuilder3.name("token").modelRef(new ModelRef("string")).parameterType("header").required(true)
				.build();
		// aParameters.add(aParameterBuilder1.build());
		// aParameters.add(aParameterBuilder2.build());
		aParameters.add(aParameterBuilder3.build());
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.mindtree.ordermanagementservice.api"))

				.paths(PathSelectors.any()).build().globalOperationParameters(aParameters);
	}
}
