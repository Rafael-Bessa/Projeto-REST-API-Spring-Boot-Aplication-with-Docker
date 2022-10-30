package bessa.morangon.rafael.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bessa.morangon.rafael.challenge.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByUsername(String username);

}
