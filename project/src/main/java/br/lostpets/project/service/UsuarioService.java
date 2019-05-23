package br.lostpets.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.lostpets.project.model.PetPerdido;
import br.lostpets.project.model.Usuario;
import br.lostpets.project.repository.AnimaisAchadosRepository;
import br.lostpets.project.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AnimaisAchadosRepository animaisAchados;

	public List<Usuario> encontrarTodos() {
		return usuarioRepository.findAll();
	}

	public Usuario encontrar(int id) {
		return usuarioRepository.unicoUsuario(id);
	}

	public void salvarUsuario(Usuario usuario) {

		if (!this.isValidAttributes(usuario)) {
			return;
		}
		usuarioRepository.save(usuario);
	}

	public Usuario emailSenha(String email, String senha) {
		return usuarioRepository.validarAcessoUsuario(email, senha);
	}

	public boolean emailSenhaExiste(String email, String senha) {
		return usuarioRepository.verificarAcesso(email, senha);
	}

	public boolean verificarEmail(String email) {
		Usuario usuario = usuarioRepository.encontrarEmail(email);
		if (usuario != null) {
			return (usuario.getCep() != null) && (usuario.getSenha() != null);
		}
		return false;
	}

	public int totalPontosUsuario(Integer idUsuario) {
		Usuario usuario = usuarioRepository.getOne(idUsuario);
		Integer total = animaisAchados.totalPontosUsuario(usuario);
		if (total == null) {
			return 0;
		}
		return total;
	}

	private boolean isValidAttributes(Usuario usuario) {

		if (usuario == null) {
			return false;
		}
		if (this.isEmptyOrNull(usuario.getNome())) {
			return false;
		}
		if (this.isEmptyOrNull(usuario.getTelefoneFixo())) {
			return false;
		}
		if (this.isEmptyOrNull(usuario.getTelefoneCelular())) {
			return false;
		}
		if (this.isEmptyOrNull(usuario.getEmail())) {
			return false;
		}
		if (this.isEmptyOrNull(usuario.getSenha())) {
			return false;
		}
		if (usuario.getCep() == null) {
			return false;
		}
		if (this.isEmptyOrNull(usuario.getRua())) {
			return false;
		}
		if (usuario.getBairro() == null) {
			return false;
		}
		if (this.isEmptyOrNull(usuario.getCidade())) {
			return false;
		}
		if (this.isEmptyOrNull(usuario.getLatitude())) {
			return false;
		}
		if (this.isEmptyOrNull(usuario.getLongitude())) {
			return false;
		}
		if (this.isEmptyOrNull(usuario.getUf())) {
			return false;
		}
		if (this.isEmptyOrNull(usuario.getUltimoAcesso())) {
			usuario.setUltimoAcesso(usuario.getAddCadastro());
			return true;
		}
		return true;
	}

	private boolean isEmptyOrNull(String text) {
		if (text == null) {
			return true;
		}
		if (text.isEmpty()) {
			return true;
		}
		return false;
	}
}
