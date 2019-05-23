package br.lostpets.project.spock

import static org.junit.Assert.assertEquals

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import br.lostpets.project.model.Usuario
import br.lostpets.project.service.UsuarioService
import spock.lang.Specification

@SpringBootTest
class SpockUsuário extends Specification {

	@Autowired
	private UsuarioService usuarioService;
	private Usuario usuario;
	def resposta;

	def 'Deveria retornar o usuario ao salvar um usuário com sucesso'() {
		when:
		usuario = new Usuario("Nome", "Fixo", "Celular","Email","Senha","Imagem","Cep","Rua","Bairro","Cidade","Uf","Latitude","Longitude");
		resposta = usuarioService.salvarUsuario(usuario)
		def Usuario u = usuarioService.encontrar(usuario.getIdPessoa())
		then:
		println "\n"+u+"\n"
	}

	def 'Deveria retornar os usuários quando existirem usuários'(){
		when:
		resposta = usuarioService.encontrarTodos()
		then:
		def i = 0;
		resposta.each{
			println "\n"+resposta[i]+"\n"
			i++
		}
	}

	def 'Deveria retornar null ao não encontrar o usuário'(){
		when:
		resposta = usuarioService.encontrar(39)
		then:
		resposta == null
	}

	def 'Deveria retornar os dados ao encontrar o usuário'(){
		when:
		resposta = usuarioService.encontrar(7)
		then:
		println "\n"+resposta+"\n"
	}

	def 'Deveria retornar null ao tentar salvar um usuário inválido'(){
		when:
		usuario = null
		resposta = usuarioService.salvarUsuario(usuario)
		then:
		resposta == null
	}

	def 'Deveria retornar null ao tentar cadastrar usuário sem nome'(){
		when:
		usuario = new Usuario(null, "Fixo", "Celular","Email","Senha","Imagem","Cep","Rua","Bairro","Cidade","Uf","Latitude","Longitude");
		resposta = usuarioService.salvarUsuario(usuario);
		then:
		resposta == null;
	}

	def 'Deveria retornar null ao tentar cadastrar usuário sem celular'(){
		when:
		usuario = new Usuario("Nome", "Fixo", null, "Email","Senha","Imagem","Cep","Rua","Bairro","Cidade","Uf","Latitude","Longitude");
		resposta = usuarioService.salvarUsuario(usuario);
		then:
		resposta == null;
	}

	def 'Deveria retornar null ao tentar cadastrar usuário sem email'(){
		when:
		usuario = new Usuario("Nome", "Fixo", "Celular",null,"Senha","Imagem","Cep","Rua","Bairro","Cidade","Uf","Latitude","Longitude");
		resposta = usuarioService.salvarUsuario(usuario);
		then:
		resposta == null;
	}

	def 'Deveria retornar null ao tentar cadastrar usuário sem senha'(){
		when:
		usuario = new Usuario("Nome", "Fixo", "Celular","Email",null,"Imagem","Cep","Rua","Bairro","Cidade","Uf","Latitude","Longitude");
		resposta = usuarioService.salvarUsuario(usuario);
		then:
		resposta == null;
	}

	def 'Deveria retornar null ao tentar cadastrar usuário sem cep'(){
		when:
		usuario = new Usuario("Nome", "Fixo", "Celular","Email","Senha","Imagem",null,"Rua","Bairro","Cidade","Uf","Latitude","Longitude");
		resposta = usuarioService.salvarUsuario(usuario);
		then:
		resposta == null;
	}

	def 'Deveria retornar null ao tentar cadastrar usuário sem bairro'(){
		when:
		usuario = new Usuario("Nome", "Fixo", "Celular","Email","Senha","Imagem","Cep","Rua",null,"Cidade","Uf","Latitude","Longitude");
		resposta = usuarioService.salvarUsuario(usuario);
		then:
		resposta == null;
	}

	def 'Deveria retornar null ao tentar cadastrar usuário sem cidade'(){
		when:
		usuario = new Usuario("Nome", "Fixo", "Celular","Email","Senha","Imagem","Cep","Rua","Bairro",null,"Uf","Latitude","Longitude");
		resposta = usuarioService.salvarUsuario(usuario);
		then:
		resposta == null;
	}

	def 'Deveria retornar null ao tentar cadastrar usuário sem uf'(){
		when:
		usuario = new Usuario("Nome", "Fixo", "Celular","Email","Senha","Imagem","Cep","Rua","Bairro","Cidade",null,"Latitude","Longitude");
		resposta = usuarioService.salvarUsuario(usuario);
		then:
		resposta == null;
	}

	def 'Deveria retornar null ao tentar cadastrar usuário sem latitude'(){
		when:
		usuario = new Usuario("Nome", "Fixo", "Celular","Email","Senha","Imagem","Cep","Rua","Bairro","Cidade","uf",null,"Longitude");
		resposta = usuarioService.salvarUsuario(usuario);
		then:
		resposta == null;
	}

	def 'Deveria retornar null ao tentar cadastrar usuário sem longitude'(){
		when:
		usuario = new Usuario("Nome", "Fixo", "Celular","Email","Senha","Imagem","Cep","Rua","Bairro","Cidade","Uf","Latitude",null);
		resposta = usuarioService.salvarUsuario(usuario);
		then:
		resposta == null;
	}
}

