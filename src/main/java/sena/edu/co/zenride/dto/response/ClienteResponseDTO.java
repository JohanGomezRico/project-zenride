package sena.edu.co.zenride.dto.response;

import lombok.Data;

@Data
public class ClienteResponseDTO {
    // Aquí SÍ va el ID para que Angular sepa qué cliente es (por si luego lo quiere editar o borrar)
    private Long id;
    private String documento;
    private String nombre;
    private String telefono;
}
