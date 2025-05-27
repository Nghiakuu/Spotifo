package vn.edu.nghiant.Spotifo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/register", "/css/**", "/js/**", "/images/**", 
                           "/artist/**", "/category/**", "/", "/play/**").permitAll() // Cho phép truy cập không cần đăng nhập
            .anyRequest().authenticated() // Tất cả các request khác đều cần phải đăng nhập
        )
        .formLogin(form -> form
            .loginPage("/login") // Trang đăng nhập tùy chỉnh
            .defaultSuccessUrl("/", true) // Trang chuyển hướng sau khi đăng nhập thành công
            .failureUrl("/login?error=true") // Trang chuyển hướng khi đăng nhập thất bại
            .permitAll()
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/")
            .permitAll()
        )
        .csrf(csrf -> csrf.disable()); // Tạm thời tắt CSRF để test
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}