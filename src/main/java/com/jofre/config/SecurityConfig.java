package com.jofre.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.jofre.domain.PerfilTipo;
import com.jofre.service.UsuarioService;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
	
	private static final String ADMIN = PerfilTipo.ADMIN.getDesc();
    private static final String ESPECIALISTA = PerfilTipo.ESPECIALISTA.getDesc();
    private static final String PESSOA = PerfilTipo.PESSOA.getDesc();
    private static final String DISCIPULADO = PerfilTipo.DISCIPULADO.getDesc();
    private static final String CAMPANHA = PerfilTipo.CAMPANHA.getDesc();
	 
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests((authorize) -> authorize				
			// acessos públicos liberados
			.requestMatchers("/webjars/**", "/css/**", "/image/**", "/js/**").permitAll()
			.requestMatchers("/", "/home", "/expired").permitAll()
			.requestMatchers("/u/novo/cadastro", "/u/cadastro/realizado", "/u/cadastro/pessoa/salvar").permitAll()
			.requestMatchers("/u/confirmacao/cadastro").permitAll()
			.requestMatchers("/u/p/**").permitAll()
			.requestMatchers("/congregacoes/**").permitAll()
			.requestMatchers("/convertidos/**").permitAll()
			
			// acessos privados admin
			.requestMatchers("/u/editar/senha", "/u/confirmar/senha").hasAnyAuthority(PESSOA, ESPECIALISTA, DISCIPULADO, CAMPANHA)
			.requestMatchers("/u/**").hasAuthority(ADMIN)

			// acessos privados especialistas
			.requestMatchers("/especialistas/especialidade/titulo/*").hasAnyAuthority(PESSOA, ESPECIALISTA, DISCIPULADO, CAMPANHA)
			.requestMatchers("/especialistas/dados", "/especialistas/salvar", "/especialistas/editar").hasAnyAuthority(ESPECIALISTA, ADMIN)
			.requestMatchers("/especialistas/**").hasAuthority(ESPECIALISTA)
//			.requestMatchers("/agendamentos/**").hasAuthority(ESPECIALISTA)

			// acessos privados pessoas
			.requestMatchers("/pessoas/**").hasAnyAuthority(PESSOA,DISCIPULADO)
			.requestMatchers("/convertidos/**").hasAnyAuthority(PESSOA, DISCIPULADO,CAMPANHA)
			
			.requestMatchers("/discipulados/**").hasAnyAuthority(DISCIPULADO,CAMPANHA)
			
			// acessos privados especialidades
			.requestMatchers("/especialidades/datatables/server/especialista/*").hasAnyAuthority(ESPECIALISTA, ADMIN)
			.requestMatchers("/especialidades/titulo").hasAnyAuthority(ESPECIALISTA, ADMIN, PESSOA, CAMPANHA)
			.requestMatchers("/especialidades/**").hasAuthority(ADMIN)
			
			// acessos privados congregações
			.requestMatchers("/congregacoes/**").hasAuthority(ADMIN)
			.anyRequest().authenticated()
		)
		.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/", true)
			.failureUrl("/login-error")
			.permitAll()
		.and()
			.logout()
			.logoutSuccessUrl("/")
			.deleteCookies("JSESSIONID")
		.and()
			.exceptionHandling()
			.accessDeniedPage("/acesso-negado")
		.and()
			.rememberMe();
		
		http.sessionManagement()
				.maximumSessions(1)
				.expiredUrl("/expired")
				.maxSessionsPreventsLogin(false)
				.sessionRegistry(sessionRegistry());

		http.sessionManagement()
				.sessionFixation()
				.newSession()
				.sessionAuthenticationStrategy(sessionAuthStrategy());
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, 
													   PasswordEncoder passwordEncoder, 
													   UsuarioService userDetailsService) throws Exception {
		
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder)
				.and()
				.build();
	}
	
	@Bean
	public SessionAuthenticationStrategy sessionAuthStrategy() {
		return new RegisterSessionAuthenticationStrategy(sessionRegistry());
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public ServletListenerRegistrationBean<?> servletListenerRegistrationBean() {
		return new ServletListenerRegistrationBean<>( new HttpSessionEventPublisher() );
	}	
}
