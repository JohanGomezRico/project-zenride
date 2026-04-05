package sena.edu.co.zenride.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Obtenemos el token de la cabecera HTTP
        final String token = getTokenFromRequest(request);
        final String username;

        // Si no hay token, lo dejamos seguir (Spring Security lo bloqueará más adelante si la ruta era protegida)
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Si hay token, extraemos el usuario
        username = jwtService.getUsernameFromToken(token);

        // 3. Validamos que el usuario exista y no esté ya autenticado en este hilo
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Buscamos al usuario en la base de datos
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Verificamos si la firma es correcta y no ha expirado
            if (jwtService.isTokenValid(token, userDetails)) {
                
                // Le decimos a Spring Security: "Todo está en orden, dale paso VIP"
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    // Método de utilidad para limpiar la cabecera y sacar solo el token
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Quitamos la palabra "Bearer "
        }
        return null;
    }
}