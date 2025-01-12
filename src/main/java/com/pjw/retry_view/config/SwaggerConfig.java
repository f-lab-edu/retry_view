package com.pjw.retry_view.config;

import com.pjw.retry_view.dto.ExampleHolder;
import com.pjw.retry_view.enums.ApiResponseCodeExamples;
import com.pjw.retry_view.enums.ResponseCode;
import com.pjw.retry_view.response.ErrorResponse;
import com.pjw.retry_view.util.JWTUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@OpenAPIDefinition(info = @Info(title = "RetryView", description = "RetryView API Document", version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(JWTUtil.AUTH_KEY);
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
        return new OpenAPI().components(
                new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Collections.singletonList(securityRequirement));
    }

    @Bean
    public OperationCustomizer customize(){
        return (Operation operation, HandlerMethod handlerMethod) -> {
            ApiResponseCodeExamples apiResponseCodeExamples = handlerMethod.getMethodAnnotation(ApiResponseCodeExamples.class);

            if(apiResponseCodeExamples != null){
                generateErroeCodeExamples(operation, apiResponseCodeExamples.value());
            }

            return operation;
        };
    }

    //에러 응답값 예시 설정
    public void generateErroeCodeExamples(Operation operation, ResponseCode[] codes){
        ApiResponses responses = operation.getResponses();

        // ExampleHolder(에러 응답값) 객체를 만들고 에러 코드별로 그룹화
        Map<Integer, List<ExampleHolder>> statusWithExampleHolders = Arrays.stream(codes)
            .map(
                code -> ExampleHolder.builder()
                        .holder(getSwaggerResponseExample(code))
                        .code(code.getHttpStatus().value())
                        .name(code.toString())
                        .build()
            )
            .collect(Collectors.groupingBy(ExampleHolder::getCode));

        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    public Example getSwaggerResponseExample(ResponseCode code){
        Example ex = new Example();
        ErrorResponse erroeResponse = ErrorResponse.from(code);
        ex.setValue(erroeResponse);
        return ex;
    }

    private void addExamplesToResponses(ApiResponses responses, Map<Integer, List<ExampleHolder>> statusWithExampleHolders){
        statusWithExampleHolders.forEach(
            (status, v) -> {
                Content content = new Content();
                MediaType mediaType = new MediaType();
                ApiResponse apiResponse = new ApiResponse();
                v.forEach(exampleHolder ->
                    mediaType.addExamples(exampleHolder.getName(), exampleHolder.getHolder())
                );

                content.addMediaType("application/json",mediaType);
                apiResponse.setContent(content);
                responses.addApiResponse(String.valueOf(status), apiResponse);
            }
        );
    }
}
