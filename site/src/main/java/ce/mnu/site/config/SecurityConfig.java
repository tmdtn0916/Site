package ce.mnu.site.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private static final String[] AUTH_WHITELIST = {
			"/siteuser", "/siteuser/**", "/css/**", "/templates/**","/**","/resource/**"
	};
	
	
	@Bean
	protected SecurityFilterChain config(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(authorize -> authorize.
						shouldFilterAllDispatcherTypes(false)
						.requestMatchers(AUTH_WHITELIST)
						.permitAll()
						.anyRequest()
						.authenticated())
				.formLogin(login -> login
						.loginPage("/siteuser/login")
						.loginProcessingUrl("/siteuser/login")
						.usernameParameter("email")
						.passwordParameter("passwd")
						.defaultSuccessUrl("/siteuser",true)
						.permitAll())
				.build();
	}

}
