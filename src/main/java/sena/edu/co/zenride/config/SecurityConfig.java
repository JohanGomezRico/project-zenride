package sena.edu.co.zenride.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authProvider;
    
    // NOTA: Aquí inyectaremos el filtro JWT más adelante
    private final JwtAuthenticationFilter jwtAuthFilter; 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // 1. Desactivamos CSRF porque no usamos cookies, usaremos tokens JWT
            .csrf(csrf -> csrf.disable())
            
            // 2. Configuramos las rutas
            .authorizeHttpRequests(authRequest ->
                authRequest
                    // Dejamos pasar libremente cualquier petición a la ruta de login y registro
                    .requestMatchers("/api/auth/**", "/error").permitAll()
                    
                    // 👇 ¡NUEVO! Le decimos al guardia que deje pasar a Swagger
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                    
                    // Exigimos token para cualquier otra ruta (bicicletas, inventario, ventas, etc.)
                    .anyRequest().authenticated()
            )
            
            // 3. Le decimos que no guarde sesiones (STATELESS), cada petición debe traer su token
            .sessionManagement(sessionManager ->
                sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // 4. Agregamos nuestro proveedor configurado en el paso anterior
            .authenticationProvider(authProvider)
            
            // 5. Aquí agregaremos nuestro filtro personalizado antes del filtro de Spring
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            
            .build();
    }
}