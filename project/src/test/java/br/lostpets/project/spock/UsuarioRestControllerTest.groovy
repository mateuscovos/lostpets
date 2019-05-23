package br.lostpets.project.spock

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus

import br.lostpets.project.ProjectApplication
import br.lostpets.project.controller.UsuarioRestController
import br.lostpets.project.service.UsuarioService
import spock.lang.Specification

@SpringBootTest(classes=ProjectApplication)
class UsuarioRestControllerTest extends Specification{

	@Autowired
	UsuarioRestController usuarioRestController;
	
	@Autowired
	UsuarioService usuarioService;	
	
	def'Retornar status 200 caso obtenha total de pontos do usuario'(){
		when:
		def resposta = usuarioRestController.getTotalPontos(1)
		
		then:
		resposta.statusCode == HttpStatus.OK
		!resposta.body
	}
}
