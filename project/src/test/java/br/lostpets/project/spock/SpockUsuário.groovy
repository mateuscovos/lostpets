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

	def 'Deveria retornar true ao salvar o usuário'() {
		given:
		usuario = new Usuario("Nome", "Fixo", "Celular","Email","Senha","Imagem","Cep","Rua","Bairro","Cidade","Uf","Latitude","Longitude");
		when:
		resposta = usuarioService.salvarUsuario(usuario)
		then:
		resposta == true;
	}

	def 'Deveria retornar os usuários quando existirem usuários'(){
		when:
		resposta = usuarioService.encontrarTodos()
		then:
		def i = 0;
		resposta.each{
			println resposta[i]
			i++
		}
	}
}

