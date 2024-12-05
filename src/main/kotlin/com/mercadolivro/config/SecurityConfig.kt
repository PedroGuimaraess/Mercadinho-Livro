package com.mercadolivro.config

import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.service.UserDetailsCustomService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import security.AuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
//    @Autowired
//    private val authenticationManager: AuthenticationManager,
//    private val customerRepository: CustomerRepository,
//    private val userDetails: UserDetailsCustomService

) {
    
    
    private val PUBLIC_MATCHERS = arrayOf<String>()
    
    private val PUBLIC_POST_MATCHERS = arrayOf(
        "/customers",
        "/book"
    )

//    @Bean
//    fun authenticationProvider(): DaoAuthenticationProvider {
//        val authProvider = DaoAuthenticationProvider()
//
//        authProvider.setUserDetailsService(userDetails)
//        authProvider.setPasswordEncoder(encoder())
//
//        return authProvider
//    }
//
//    @Bean
//    fun authenticationManager(@Lazy authConfiguration: AuthenticationConfiguration): AuthenticationManager {
//        return authConfiguration.authenticationManager
//    }


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
            //.authenticationProvider(authenticationProvider())
            //.addFilter(AuthenticationFilter(authenticationManager,customerRepository ))
            .sessionManagement{ sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()
    }

    @Bean
    fun encoder(): BCryptPasswordEncoder{
       return  BCryptPasswordEncoder()
    }

}