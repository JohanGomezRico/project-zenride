package sena.edu.co.zenride.services;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import sena.edu.co.zenride.config.JwtService;
import sena.edu.co.zenride.dto.request.LoginRequestDTO;
import sena.edu.co.zenride.dto.request.RegisterRequestDTO;
import sena.edu.co.zenride.dto.response.AuthResponseDTO;
import sena.edu.co.zenride.entities.Usuario;
import sena.edu.co.zenride.repository.UsuarioRepository;
import sena.edu.co.zenride.dto.response.UsuarioResponseDTO;

@Service
@RequiredArgsConstructor
public class IAuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final sena.edu.co.zenride.repository.ClienteRepository clienteRepository;

    // 1. Método para iniciar sesión
    public AuthResponseDTO login(LoginRequestDTO request) {
        // Esto lanza un error automáticamente si la contraseña es incorrecta
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        // Si pasa de la línea anterior, el usuario es válido. Lo buscamos y generamos token.
        UserDetails user = usuarioRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        
        return AuthResponseDTO.builder()
                .token(token)
                .build();
    }

    // 2. Método para registrar un nuevo administrador/vendedor
    public AuthResponseDTO register(RegisterRequestDTO request) {
        // Creamos la entidad Usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        // ¡SUPER IMPORTANTE! Guardamos la contraseña encriptada, nunca en texto plano
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(request.getRol());
        usuario.setEmail(request.getEmail());

        // Lo guardamos en la base de datos
        usuarioRepository.save(usuario);

        // Opcional: Devolver un token inmediatamente después de registrarse
        return AuthResponseDTO.builder()
                .token(jwtService.getToken(usuario))
                .build();
    }
    
 // 👇 NUEVO MÉTODO: Listar todos los usuarios de forma segura
    public List<UsuarioResponseDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll().stream().map(usuario -> {
            UsuarioResponseDTO dto = new UsuarioResponseDTO();
            dto.setId(usuario.getId());
            dto.setUsername(usuario.getUsername());
            dto.setEmail(usuario.getEmail());
            // Convertimos el Enum a String
            dto.setRol(usuario.getRol().name()); 
            return dto;
        }).collect(Collectors.toList());
    }
    
 // 1. Eliminar usuario
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // 2. Cambiar rol
    public void cambiarRol(Long id, Usuario.Rol nuevoRol) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setRol(nuevoRol);
        usuarioRepository.save(usuario);
    }
}
