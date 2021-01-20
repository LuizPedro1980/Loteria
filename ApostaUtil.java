package br.com.loteria.util;

import static java.util.Arrays.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.loteria.model.Aposta;

import br.com.loteria.model.Usuario;
import br.com.loteria.repository.ApostaRepository;
import br.com.loteria.repository.UsuarioRepository;



public final class ApostaUtil {
	
	public static String gerarNumeroNovaAposta(List<Aposta> apostasJaRealizadas) {
		int[] builderArray = new int[6];
		Random randomGenerator = new Random();
		for(int i = 0 ; i <= 5 ; i++) {
			builderArray[i] = randomGenerator.nextInt(60);
         }
		sort(builderArray);
		String generatedNumber = Arrays.toString(builderArray);
		
		Aposta novaAposta = new Aposta(generatedNumber);
		
		if (apostasJaRealizadas.contains(novaAposta)) {
			gerarNumeroNovaAposta(apostasJaRealizadas);
		} 
		
		return generatedNumber;
	}
	
	public static Usuario cadastrarUsuarioInexistente(Usuario usuarioRequest, @Autowired UsuarioRepository usuarioRepository) {
		Usuario usuario = null;
		Optional<Usuario> usuarioOptional =  usuarioRepository.findByEmail(usuarioRequest.getEmail());
		if (usuarioOptional.isPresent()) {
			usuario = usuarioOptional.get();
		} else {
			usuario = new Usuario();
			usuario.setEmail(usuarioRequest.getEmail());
			usuarioRepository.save(usuario);
		}
		
		return usuario;
	}
	
	public static Aposta cadastrarAposta(Usuario usuario, @Autowired ApostaRepository apostaRepository) {
		List<Aposta> apostasJaRealizadasUsuario = apostaRepository.findByUsuario(usuario);
		Aposta aposta = new Aposta();
		aposta.setUsuario(usuario);
		aposta.setNumeroGerado(ApostaUtil.gerarNumeroNovaAposta(apostasJaRealizadasUsuario));
		return apostaRepository.save(aposta);
	}
	
	public static List<Aposta> verificarExistenciaUsuario(Optional<Usuario> usuarioOptional, ApostaRepository apostaRepository) throws Exception{
		return usuarioOptional.map(u -> { 
			return apostaRepository.findByUsuario(u);
		}).orElseThrow(() -> new Exception("Usuário não cadastrado"));
		
	}

}
