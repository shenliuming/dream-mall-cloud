package edu.goods.cloud.dream.handler;

import edu.common.cloud.dream.entity.base.AdminUserToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
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
public class GoodsSwagger3Config {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .ignoredParameterTypes(AdminUserToken.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.goods.cloud.dream.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(getGlobalRequestParameters());
    }

    //生成全局通用参数
    private List<RequestParameter> getGlobalRequestParameters() {
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .name("token")
                .description("登录认证token")
                .required(false) // 非必传
                .in(ParameterType.HEADER) //请求头中的参数，其它类型可以点进ParameterType类中查看
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .build());
        return parameters;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("dream-mall-cloud-goods-service 接口文档")
                .description("swagger接口文档")
                .version("3.0")
                .build();
    }
}