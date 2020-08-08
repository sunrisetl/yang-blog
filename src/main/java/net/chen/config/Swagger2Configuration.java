package net.chen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created by Chen
 * 2020/8/1 16:11
 */

@Configuration
public class Swagger2Configuration {
    //版本
    public static final String VERSION = "1.0.0";
    /** 接口
     *  接口前缀:portal
     * */

    @Bean
    public Docket portalApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(portalApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.chen.controller.portal"))
                .paths(PathSelectors.any())
                .build()
                .groupName("前端门户");
    }

    private ApiInfo portalApiInfo() {
        return new ApiInfoBuilder()
                .title("Ay博客系统门户接口文档") //设置文档的标题
                .description("portal接口文档")
                .version(VERSION)   // 设置文档信息版本
                .build();
    }

    /** 管理员中心接口*/
    @Bean
    public Docket AdminApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(adminApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.chen.controller.admin"))
                .paths(PathSelectors.any())
                .build()
                .groupName("管理中心");
    }

    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("Ay博客系统管理中心接口文档") //设置文档的标题
                .description("admin接口文档")
                .version(VERSION)   // 设置文档信息版本
                .build();
    }

    /** 用户中心*/
    @Bean
    public Docket UserApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(userApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.chen.controller.user"))
                .paths(PathSelectors.any())
                .build()
                .groupName("用户中心");
    }

    private ApiInfo userApiInfo() {
        return new ApiInfoBuilder()
                .title("Ay博客系统用户中心接口文档") //设置文档的标题
                .description("user接口文档")
                .version(VERSION)   // 设置文档信息版本
                .build();
    }
}
