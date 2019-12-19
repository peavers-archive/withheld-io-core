/* Licensed under Apache-2.0 */
package space.forloop.project.configuration;

import com.google.firebase.auth.FirebaseAuth;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import space.forloop.project.security.bearer.BearerTokenReactiveAuthenticationManager;
import space.forloop.project.security.bearer.ServerHttpBearerAuthenticationConverter;
import space.forloop.project.security.jwt.JWTCustomVerifier;

/** @author Chris Turner (chris@forloop.space) */
@Configuration
public class SecurityConfig {

  private final FirebaseAuth firebaseAuth;

  public SecurityConfig(final FirebaseAuth firebaseAuth) {

    this.firebaseAuth = firebaseAuth;
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);
    configuration.setAllowedOrigins(Collections.singletonList("*"));
    configuration.setAllowedMethods(Collections.singletonList("*"));
    configuration.setAllowedHeaders(Collections.singletonList("*"));

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }

  /**
   * For Spring Security webflux, a chain of filters will provide user authentication and
   * authorization, we add custom filters to enable JWT token approach.
   *
   * @param http An initial object to build common filter scenarios. Customized filters are added
   *     here.
   * @return SecurityWebFilterChain A filter chain for web exchanges that will provide security
   */
  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(final ServerHttpSecurity http) {

    http.authorizeExchange()
        .and()
        .authorizeExchange()
        .pathMatchers("/v1/**")
        .authenticated()
        .and()
        .addFilterAt(bearerAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
        .csrf()
        .disable();

    return http.build();
  }

  /**
   * Use the already implemented logic by AuthenticationWebFilter and set a custom converter that
   * will handle requests containing a Bearer token inside the HTTP Authorization header. Set a
   * dummy authentication manager to this filter, it's not needed because the converter handles
   * this.
   *
   * @return bearerAuthenticationFilter that will authorize requests containing a JWT
   */
  private AuthenticationWebFilter bearerAuthenticationFilter() {
    final AuthenticationWebFilter bearerAuthenticationFilter;

    bearerAuthenticationFilter =
        new AuthenticationWebFilter(new BearerTokenReactiveAuthenticationManager());

    bearerAuthenticationFilter.setAuthenticationConverter(
        new ServerHttpBearerAuthenticationConverter(new JWTCustomVerifier(firebaseAuth)));
    bearerAuthenticationFilter.setRequiresAuthenticationMatcher(
        ServerWebExchangeMatchers.pathMatchers("/v1/**"));

    return bearerAuthenticationFilter;
  }
}
