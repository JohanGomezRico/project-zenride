package sena.edu.co.zenride.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Este será el nombre con el que inician sesión (ej. miguel_admin)
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    // Aquí guardaremos la contraseña (¡siempre encriptada, nunca en texto plano!)
    @Column(nullable = false)
    private String password;

    // Para saber si es ADMINISTRADOR o VENDEDOR
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    public enum Rol {
        ADMIN, VENDEDOR
    }

    // ========================================================================
    // MÉTODOS OBLIGATORIOS DE USERDETAILS (Para que Spring Security nos entienda)
    // ========================================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Le dice a Spring Security qué rol tiene este usuario
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // La cuenta no expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // La cuenta no está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Las credenciales no expiran
    }

    @Override
    public boolean isEnabled() {
        return true; // El usuario está activo
    }
}