package sena.edu.co.zenride.dto.request;

import lombok.Data;

@Data
public class ClienteRequestDTO {
    private String documento;
    private String nombre;
    private String telefono;
    private String correo;
}