package konkuk.server.test.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable);

        http
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/", "/login", "/loginout", "/error", "/gmail/login").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                );

        return http.build();
    }

    @Bean
    public WebClient webCLient() {
        // baseUrl 추가
        return WebClient.builder()
                .baseUrl("https://gmail.googleapis.com/gmail/v1/users")
                .build();
    }

//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // 숫자 타입의 처리 방식을 조정
//        objectMapper.configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
//        return objectMapper;
//    }
}
