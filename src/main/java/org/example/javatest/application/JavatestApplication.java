package org.example.javatest.application;

import org.example.javatest.util.LoggedUser;
import org.example.javatest.controller.AuthenticationController;
import org.example.javatest.db.UserRepository;
import org.example.javatest.filter.AuthorizationFilter;
import org.example.javatest.model.UserData;
import org.example.javatest.model.WordEntry;
import org.example.javatest.service.AuthenticationService;
import org.example.javatest.util.TokenHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackageClasses = {AuthenticationController.class, AuthenticationService.class, AuthorizationFilter.class, LoggedUser.class})
@EntityScan(basePackageClasses = {UserData.class, WordEntry.class})
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class JavatestApplication {
    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilter(@Qualifier("loggedUser") LoggedUser loggedUser, @Qualifier("tokenHelper") TokenHelper tokenHelper) {
        FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthorizationFilter(loggedUser, tokenHelper));
        registrationBean.addUrlPatterns("/game/*", "/board/*");

        return registrationBean;
    }

    @Bean(name = "loggedUser")
    public LoggedUser loggedUser() {
        return new LoggedUser();
    }

    @Bean(name = "tokenHelper")
    public TokenHelper tokenHelper() {
        return new TokenHelper();
    }

    public static void main(String[] args) {
        SpringApplication.run(JavatestApplication.class, args);
    }

}
