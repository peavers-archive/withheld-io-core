/* Licensed under Apache-2.0 */
package space.forloop.project.configuration;

import com.google.firebase.auth.FirebaseAuth;
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
import space.forloop.project.security.jwt.FirebaseAuthenticationConverter;

import java.util.Collections;

/** @author Chris Turner (chris@forloop.space) */
@Configuration
public class SecurityConfig {

  private static final String SECURE_ROUTE = "/v1/**";

  private static final String ALLOWED_CORS = "*";

  private final FirebaseAuth firebaseAuth;

  public SecurityConfig(final FirebaseAuth firebaseAuth) {

    this.firebaseAuth = firebaseAuth;
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);
    configuration.setAllowedOrigins(Collections.singletonList(ALLOWED_CORS));
    configuration.setAllowedMethods(Collections.singletonList(ALLOWED_CORS));
    configuration.setAllowedHeaders(Collections.singletonList(ALLOWED_CORS));

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration(SECURE_ROUTE, configuration);

    return source;
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(final ServerHttpSecurity http) {

    return http.authorizeExchange()
        .and()
        .authorizeExchange()
        .pathMatchers(SECURE_ROUTE)
        .authenticated()
        .and()
        .addFilterAt(firebaseAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
        .csrf()
        .disable()
        .build();
  }

  @Bean
  public AuthenticationWebFilter firebaseAuthenticationFilter() {

    final BearerTokenReactiveAuthenticationManager authenticationManager =
        new BearerTokenReactiveAuthenticationManager();

    final FirebaseAuthenticationConverter authenticationConverter =
        new FirebaseAuthenticationConverter(firebaseAuth);

    final AuthenticationWebFilter webFilter = new AuthenticationWebFilter(authenticationManager);

    webFilter.setServerAuthenticationConverter(authenticationConverter);
    webFilter.setRequiresAuthenticationMatcher(
        ServerWebExchangeMatchers.pathMatchers(SECURE_ROUTE));

    return webFilter;
  }
}
