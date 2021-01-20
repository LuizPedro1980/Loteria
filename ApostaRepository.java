package br.com.loteria.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.loteria.model.Aposta;
import br.com.loteria.model.Usuario;

@Repository
public interface ApostaRepository extends JpaRepository<Aposta, Long> {
	
	@Transactional(readOnly=true)
	List<Aposta> findByUsuario(Usuario usuario);
}
