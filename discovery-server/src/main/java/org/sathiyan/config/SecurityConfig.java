//package org.sathiyan.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Value("${eureka.username}")
//    private String userName;
//    @Value("${eureka.password}")
//    private String password;
//
//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated()).httpBasic(withDefaults());
//        return http.build();
//    }
//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        UserDetails user = User.withUsername(userName)
//                .password(passwordEncoder.encode(password))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//
//
//}
