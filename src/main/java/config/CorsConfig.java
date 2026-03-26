package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a todos los endpoints de tu API (ej. /api/bicicletas, /api/clientes)
                .allowedOrigins(
                    "http://localhost:4200", // Para cuando pruebes tu frontend localmente
                    "https://frontend-zenride-ouqr.vercel.app" // IMPORTANTE: Reemplaza esto con tu URL exacta de Vercel
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
