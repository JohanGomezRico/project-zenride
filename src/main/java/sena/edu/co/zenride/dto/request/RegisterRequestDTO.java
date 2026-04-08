package sena.edu.co.zenride.dto.request;
import lombok.Data;
import sena.edu.co.zenride.entities.Usuario.Rol;

@Data
public class RegisterRequestDTO {
	private String username;
	private String password;
	private String email;
	private Rol rol;
}
