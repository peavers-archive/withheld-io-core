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
import space.forloop.project.security.bearer.BearerTokenReactiveAuthenticationManager;
import space.forloop.project.security.jwt.FirebaseAuthenticationConverter;

/** @author Chris Turner (chris@forloop.space) */
@Configuration
public class SecurityConfig {

  private final FirebaseAuth firebaseAuth;

  public SecurityConfig(final FirebaseAuth firebaseAuth) {

    this.firebaseAuth = firebaseAuth;
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(final ServerHttpSecurity http) {

    http.authorizeExchange()
        .and()
        .authorizeExchange()
        .pathMatchers("/v1/**")
        .authenticated()
        .and()
        .addFilterAt(firebaseAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
        .csrf()
        .disable();

    return http.build();
  }

  private AuthenticationWebFilter firebaseAuthenticationFilter() {
    final AuthenticationWebFilter webFilter =
        new AuthenticationWebFilter(new BearerTokenReactiveAuthenticationManager());

    webFilter.setServerAuthenticationConverter(new FirebaseAuthenticationConverter(firebaseAuth));
    webFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/v1/**"));

    return webFilter;
  }
}
