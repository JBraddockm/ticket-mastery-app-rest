package org.example.config;

import javax.sql.DataSource;
import org.example.service.impl.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableJpaAuditing
public class AppConfig {

  private final DataSource dataSource;

  public AppConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  public ModelMapper mapper() {
    return new ModelMapper();
  }

  @Bean
  AuditorAware<Long> auditorAware() {
    return new AuditorAwareImpl();
  }

  @Bean
  public ITemplateResolver svgTemplateResolver() {
    SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
    resolver.setPrefix("classpath:/static/svg/");
    resolver.setSuffix(".svg");
    resolver.setTemplateMode("XML");

    return resolver;
  }

  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
    db.setDataSource(dataSource);
    return db;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
