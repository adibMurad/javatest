package org.example.javatest.application;

import org.example.javatest.controller.AuthenticationController;
import org.example.javatest.db.UserRepository;
import org.example.javatest.filter.AuthorizationFilter;
import org.example.javatest.model.UserData;
import org.example.javatest.service.AuthenticationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackageClasses = {AuthenticationController.class, AuthenticationService.class, AuthorizationFilter.class})
@EntityScan(basePackageClasses = UserData.class)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class JavatestApplication {
    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authenticationFilter() {
        FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthorizationFilter());
        registrationBean.addUrlPatterns("/game/*", "/board/*");

        return registrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(JavatestApplication.class, args);
    }

}
