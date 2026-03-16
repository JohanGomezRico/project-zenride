package sena.edu.co.zenride.dto.request;

import lombok.Data;

@Data
public class ClienteRequestDTO {
    // Fíjate que NO ponemos el ID, porque al crearlo no existe aún
    private String documento;
    private String nombre;
    private String telefono;
}
