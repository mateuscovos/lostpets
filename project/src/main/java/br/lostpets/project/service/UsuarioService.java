package br.lostpets.project.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.lostpets.project.model.AnimaisAchados;
import br.lostpets.project.model.PontosUsuario;
import br.lostpets.project.model.Usuario;
import br.lostpets.project.repository.AnimaisAchadosRepository;
import br.lostpets.project.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired 
	private AnimaisAchadosRepository animaisAchados;
	
	public List<Usuario> encontrarTodos(){
		return usuarioRepository.findAll();
	}
	
	public Usuario encontrar(int id) {
		return usuarioRepository.unicoUsuario(id);
	}
	
	public void salvarUsuario(Usuario usuario) {
		usuarioRepository.save(usuario);		
    }

	public Usuario emailSenha(String email, String senha) {
		return usuarioRepository.validarAcesso(email, senha);
	}

	public Usuario verificarEmailUsuario(String email) {
		Usuario usuario = usuarioRepository.encontrarEmail(email);
		return usuario;		
	}
	
	public boolean verificarEmail(String email) {
		Usuario usuario = usuarioRepository.encontrarEmail(email);
		if(usuario != null) {
			return (usuario.getCep() != null) && (usuario.getSenha() != null);
		}
		return false;		
	}
	
	public int totalPontosUsuario(Integer idUsuario) {
		Usuario usuario = usuarioRepository.getOne(idUsuario);
		Integer total = animaisAchados.totalPontosUsuario(usuario);
		if(total == null) { return 0; }
		return total;
	}

	public List<PontosUsuario> totalPontosUsuarioTodosUsuario() {
		List<AnimaisAchados> animaisEncontrados = animaisAchados.findAllByStatus("A");
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		List<PontosUsuario> pontosUsuario = new ArrayList<>();
		
		for (int i = 0; i < usuarios.size(); i++) {
			Usuario u = usuarios.get(i);
			int petsAchados = 0;
			pontosUsuario.add(new PontosUsuario(u.getIdPessoa(), 0, u.getNome(), petsAchados));
			for (int j = 0; j < animaisEncontrados.size(); j++) {
				AnimaisAchados animal = animaisEncontrados.get(j);
				if(u.getIdPessoa() == animal.getUsuarioAchou().getIdPessoa()) {
					int pontos = pontosUsuario.get(i).getPontos() + animal.getPontos();
					pontosUsuario.get(i).setPontos(pontos);
					pontosUsuario.get(i).setQuantidadePetsAchados(++petsAchados);
				}
			}
		}
		pontosUsuario.sort(Comparator.comparing(PontosUsuario::getPontos).reversed());
		return pontosUsuario;
	}
	
	/*
	public void delete(Long id) {
		usuarioRepository.delete(id);
	}
	*/
	
}
