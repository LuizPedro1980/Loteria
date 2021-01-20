package br.com.loteria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.loteria.model.Aposta;
import br.com.loteria.model.Usuario;
import br.com.loteria.repository.ApostaRepository;
import br.com.loteria.repository.UsuarioRepository;
import br.com.loteria.util.ApostaUtil;


@RestController
@RequestMapping("/apostas")
public class ApostaController {
	@Autowired
	private ApostaRepository apostaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/**
	 * Lista todos as apostas associadas ao e-mail
	 * @param emailUsuario
	 * @return
	 * @throws Exception
	 */
	@GetMapping("{emailUsuario}")
	public List<Aposta> listarApostas(@PathVariable String emailUsuario) throws Exception {
		Optional<Usuario> usuarioOptional  = usuarioRepository.findByEmail(emailUsuario);
		
		
		return usuarioOptional.map(u -> { 
			return apostaRepository.findByUsuario(u);
		}).orElseThrow(() -> new Exception("Usuário não cadastrado"));
		
	}

	/**
	 * Cria uma nova aposta para um usuário, se o usuário não existar realiza primeiramente o cadastro
	 * @param usuarioRequest
	 * @return
	 * @throws Exception
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Aposta novaAposta(@RequestBody Usuario usuarioRequest) throws Exception {
		Usuario usuario = null;
		usuario = ApostaUtil.cadastrarUsuarioInexistente(usuarioRequest, usuarioRepository);
		return ApostaUtil.cadastrarAposta(usuario, apostaRepository);
	}
		

		
}
