package com.qswang.steamer.controller.apidoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("qsw")
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.qswang.steamer.controller"))
                .build();
    }

    public ApiInfo apiInfo(){
        Contact contact = new Contact("Qiushi.Wang", "https://www.linkedin.com/in/qiushi-wang-006b40205/", "qswang1988@gmail.com");
        return new ApiInfo(
                "steamer rest api",
                "steamer",
                "V1.0",
                "",
                contact,
                "",
                "",
                new ArrayList());
    }
}
