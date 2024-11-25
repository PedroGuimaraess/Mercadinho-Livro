package com.mercadolivro.config

import com.mercadolivro.repository.CustomerRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import security.AuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationManager: AuthenticationManager,
    private val customerRepository: CustomerRepository
) {
    
    
    private val PUBLIC_MATCHERS = arrayOf<String>()
    
    private val PUBLIC_POST_MATCHERS = arrayOf(
        "/customers",
        "/book"
    )

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .cors{ cors -> cors.disable() }
            .csrf{ csrf -> csrf.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(*PUBLIC_MATCHERS).permitAll()
                    .requestMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilter(AuthenticationFilter(authenticationManager,customerRepository ))
            .sessionManagement{ sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()

//            .authorizeHttpRequests{
//                    auth -> auth.requestMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll().anyRequest().authenticated()
//            }

    }

    @Bean
    fun encoder(): BCryptPasswordEncoder{
       return  BCryptPasswordEncoder()
    }

}