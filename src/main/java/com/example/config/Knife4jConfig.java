package com.example.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Slf4j
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4jConfig {

    @Bean
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 分组名称
                .groupName("2.X版本")
                .select()
                // 这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
                .paths(PathSelectors.any())
                .build();
//                .globalRequestParameters(
//                        Collections.singletonList(
//                                new RequestParameterBuilder()
//                                        .name("token")
//                                        .description("全局令牌")
//                                        .in(ParameterType.HEADER)  // 头信息
//                                        .required(false)
//                                        .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                                        .build()
//                        )
//                );

        return docket;
    }

    private ApiInfo apiInfo() {
        log.info("http://localhost:8088/doc.html");
        return new ApiInfoBuilder()
                .title("Knife4j RESTful APIs")
                .description("Knife4j API文档")
                .termsOfServiceUrl("https://doc.xiaominfo.com/")
                .version("1.0")
                .build();
    }
}
