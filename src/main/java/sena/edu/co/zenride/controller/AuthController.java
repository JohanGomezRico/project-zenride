package sena.edu.co.zenride.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sena.edu.co.zenride.dto.request.LoginRequestDTO;
import sena.edu.co.zenride.dto.request.RegisterRequestDTO;
import sena.edu.co.zenride.dto.response.AuthResponseDTO;
import sena.edu.co.zenride.services.IAuthService;

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
}
