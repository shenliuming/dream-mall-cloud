package edu.user.cloud.dream.config;

import edu.user.cloud.dream.entity.AdminUserToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestReqDtoeterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ReqDtoeterType;
import springfox.documentation.service.RequestReqDtoeter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date: 2023-11-08 16:11
 * @Author： shenliuming
 * @return：
 */
@Configuration
@EnableOpenApi
public class AdminUserSwagger3Config{

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .ignoredReqDtoeterTypes(AdminUserToken.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.user.cloud.dream.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalRequestReqDtoeters(getGlobalRequestReqDtoeters());
    }

    //生成全局通用参数
    private List<RequestReqDtoeter> getGlobalRequestReqDtoeters() {
        List<RequestReqDtoeter> ReqDtoeters = new ArrayList<>();
        ReqDtoeters.add(new RequestReqDtoeterBuilder()
                .name("token")
                .description("登录认证token")
                .required(false) // 非必传
                .in(ReqDtoeterType.HEADER) // 请求头中的参数，其它类型可以点进ReqDtoeterType类中查看
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .build());
        return ReqDtoeters;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("dream-mall-cloud-user-service接口文档")
                .description("swagger接口文档")
                .version("2.0")
                .build();
    }
}