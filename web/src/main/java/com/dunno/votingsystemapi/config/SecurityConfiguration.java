package com.dunno.votingsystemapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;

    public SecurityConfiguration(JwtFilter filter){
        this.jwtFilter = filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/auth/sign-in").anonymous()

                        // solo ADMIN
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // VOTER, CANDIDATE y ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/candidates").hasAnyRole("ADMIN", "VOTER", "CANDIDATE")
                        .requestMatchers(HttpMethod.GET, "/api/candidates/{candidateId}").hasAnyRole("ADMIN", "VOTER", "CANDIDATE")
                        .requestMatchers(HttpMethod.GET, "/api/votes/statistics").hasAnyRole("ADMIN", "VOTER", "CANDIDATE")

                        // solo VOTER
                        .requestMatchers(HttpMethod.POST, "/api/votes").hasRole("VOTER")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
