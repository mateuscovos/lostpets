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
	private UsuarioService usuarioService;
	
	def 'verificar se usuario consta na base de dados para acessar'() {
		given:
			usuario = new Usuario("nome", "telefoneFixo", "telefoneCelular", "email", "senha", "idImagem", "cep", "rua", "bairro", "cidade", "uf", "latitude", "longitude")
			usuarioService.salvarUsuario(usuario)
		expect:
			usuarioService.emailSenha(usuario.getEmail(), usuario.getSenha()) != null
	}
	

		
}
