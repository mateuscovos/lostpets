package br.lostpets.project.spock

import javax.transaction.Transactional

import org.jboss.jandex.TypeTarget.Usage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import br.lostpets.project.model.Usuario
import br.lostpets.project.service.UsuarioService
import spock.lang.*

@Transactional
@SpringBootTest
class SpockLogin extends Specification{

	private Usuario usuario; 
	private Usuario usuario1;

	@Autowired
	UsuarioService usuarioService;
	
	def 'verificar se usuario consta na base de dados para acessar'() {
		setup:
			usuario = new Usuario("nome", "telefoneFixo", "telefoneCelular", "email", "senha", "idImagem", "cep", "rua", "bairro", "cidade", "uf", "latitude", "longitude")
			usuarioService.salvarUsuario(usuario)
		when:
			usuario1 = usuarioService.emailSenha(usuario.getEmail(), usuario.getSenha())
		then:
			usuario1 != null
	}
	
	def 'verificar se login ou senha é diferente de null'(){
			given:
				usuario.setEmail("email")
				usuario.setSenha("senha")
			expect:
				usuario.getEmail() == usuario.getSenha()
			
	}
	
}
