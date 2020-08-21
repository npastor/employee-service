package com.takeaway.challenge.config;

import java.lang.reflect.WildcardType;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;
import com.takeaway.challenge.ChallengeApi;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.withClassAnnotation(ChallengeApi.class))
                                                      .paths(PathSelectors.ant("/**"))
                                                      .build()
                                                      .apiInfo(apiInfo())
                                                      .pathMapping("/")
                                                      .directModelSubstitute(LocalDate.class, String.class)
                                                      .genericModelSubstitutes(ResponseEntity.class)
                                                      .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(DeferredResult.class,
                                                                                                                          typeResolver.resolve(ResponseEntity.class,
                                                                                                                                               WildcardType.class)),
                                                                                                     typeResolver.resolve(WildcardType.class)));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Challenge API - Employee Service")
                                   .description("Challenge API - provides endpoints to manage employees and departments.")
                                   .version("1.0")
                                   .contact(new Contact("Contact Name",
                                                        "Contact URL",
                                                        "Contact Email"))
                                   .build();
    }

}
