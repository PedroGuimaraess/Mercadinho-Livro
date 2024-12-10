package com.mercadolivro.config

import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.security.AuthenticationFilter
import com.mercadolivro.security.AuthorizationFilter
import com.mercadolivro.security.JwtUtil
import com.mercadolivro.service.UserDetailsCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationConfiguration: AuthenticationConfiguration,
    private val customerRepository: CustomerRepository,
    private val userDetails: UserDetailsCustomService,
    private val jwtUtil: JwtUtil

) {

    private val PUBLIC_MATCHERS = arrayOf<String>()
    
    private val PUBLIC_POST_MATCHERS = arrayOf(
        "/customers",
        "/book"
    )

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetails)
        authenticationProvider.setPasswordEncoder(encoder())
        return authenticationProvider
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(): AuthenticationManager {
        return authenticationConfiguration.authenticationManager;
    }

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
            .authenticationProvider(authenticationProvider())
            .addFilter(AuthenticationFilter(authenticationManager(),customerRepository, jwtUtil))
            .addFilter(AuthorizationFilter(authenticationManager(), jwtUtil, userDetails))
            .sessionManagement{ sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()
    }

    @Bean
    fun encoder(): BCryptPasswordEncoder{
       return  BCryptPasswordEncoder()
    }

}