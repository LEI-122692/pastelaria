package com.vaadin.starter.bakery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.vaadin.starter.bakery.app.security.SecurityConfiguration;
import com.vaadin.starter.bakery.backend.data.entity.User;
import com.vaadin.starter.bakery.backend.repositories.UserRepository;
import com.vaadin.starter.bakery.backend.service.UserService;
import com.vaadin.starter.bakery.ui.MainView;

/**
 * Classe principal da aplicação Spring Boot da Bakery.
 *
 * <p>Configura os pacotes base para segurança, serviços,
 * repositórios e entidades, inicializando o contexto Spring.</p>
 *
 * <p>Permite executar a aplicação como JAR standalone ou
 * como WAR num servidor externo (Tomcat, Jetty, etc.)
 * através da extensão de {@link SpringBootServletInitializer}.</p>
 *
 * @author TeuNome
 * @since 1.0
 */
@SpringBootApplication(scanBasePackageClasses = { SecurityConfiguration.class, MainView.class, Application.class,
		UserService.class }, exclude = ErrorMvcAutoConfiguration.class)
@EnableJpaRepositories(basePackageClasses = { UserRepository.class })
@EntityScan(basePackageClasses = { User.class })
public class Application extends SpringBootServletInitializer {

	/**
	 * Ponto de entrada da aplicação.
	 *
	 * @param args argumentos da linha de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Configura a aplicação para deploy em servidores externos.
	 *
	 * @param application builder da aplicação Spring
	 * @return instância configurada da aplicação
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
}

