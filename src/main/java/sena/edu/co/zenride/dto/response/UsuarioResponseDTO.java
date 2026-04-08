package sena.edu.co.zenride.dto.response;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String rol;
}
