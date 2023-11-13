package org.example.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class AppConfig {

  @Bean
  public ModelMapper mapper() {
    return new ModelMapper();
  }

  @Bean
  public ITemplateResolver svgTemplateResolver() {
    SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
    resolver.setPrefix("classpath:/static/svg/");
    resolver.setSuffix(".svg");
    resolver.setTemplateMode("XML");

    return resolver;
  }
}
