package sena.edu.co.zenride.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sena.edu.co.zenride.dto.request.LoginRequestDTO;
import sena.edu.co.zenride.dto.request.RegisterRequestDTO;
import sena.edu.co.zenride.dto.response.AuthResponseDTO;
import sena.edu.co.zenride.services.IAuthService;
import java.util.List;
import sena.edu.co.zenride.dto.response.UsuarioResponseDTO;
import sena.edu.co.zenride.entities.Usuario;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(originPatterns = "*") // Permitir peticiones desde tu Angular
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    
 // 👇 NUEVO ENDPOINT: Para que tu panel de Admin obtenga la lista
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(authService.obtenerTodosLosUsuarios());
    }
    
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        authService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/usuarios/{id}/rol")
    public ResponseEntity<Void> actualizarRol(@PathVariable Long id, @RequestParam Usuario.Rol nuevoRol) {
        authService.cambiarRol(id, nuevoRol);
        return ResponseEntity.ok().build();
    }
}
