package com.recruitment.simplerecruitment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.recruitment.simplerecruitment.util.LoggingInterceptor;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class SimpleRecruitment extends WebMvcConfigurerAdapter {
    public static void main( String[] args ){
    	 SpringApplication.run(SimpleRecruitment.class, args);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName()))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(generateApiInfo());
    }
    
    private ApiInfo generateApiInfo(){
        return new ApiInfo("Recruitment Server Applicant Test Service", 
        		           "This service is to check the technology knowledge of Spring and Java.", 
        		           "Version 1.0",
                           "urn:tos", 
                           "phillipe.lemos@gmail.com", 
                           "Apache 2.0", 
                           "http://www.apache.org/licenses/LICENSE-2.0");
    }
}
