package by.kharchenko.springbootcafe.config;

import by.kharchenko.springbootcafe.controllers.interceptor.AuthenticateAdminInterceptor;
import by.kharchenko.springbootcafe.controllers.interceptor.AuthenticateClientInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;


@Configuration
public class SpringApplicationConfig implements WebMvcConfigurer {
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(new AuthenticateClientInterceptor()).addPathPatterns("/client/*");
        registry.addInterceptor(new AuthenticateAdminInterceptor()).addPathPatterns("/admin/*");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename("classpath:pagecontent");
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
        return reloadableResourceBundleMessageSource;
    }


}
