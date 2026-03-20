package sena.edu.co.zenride.dto.response;

import lombok.Data;

@Data
public class ClienteResponseDTO {
    private Long id;
    private String documento;
    private String nombre;
    private String telefono;
    private String correo;
}