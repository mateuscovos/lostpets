package br.lostpets.project.spock

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

import com.google.api.client.util.Value

import br.lostpets.project.ProjectApplication
import br.lostpets.project.controller.LoginController
import spock.lang.Specification

@WebMvcTest(LoginController)
@ContextConfiguration(classes=ProjectApplication.class)
class UrlRequest extends Specification{

	@Autowired
	MockMvc mock;

	@Autowired
	LoginController loginController;

	@Value('${spring.mvc.view.prefix}')
	String viewPrefix;

	def 'Deveria retornar status 200 e redirecionar p/ View correta'(){
		given:
		def destino = "login"

		when:
		def resposta = mvc.perform(MockMvcRequestBuilders.get("/").param("usuario", "Natal"))

		then:
		resposta
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.forwardedUrl("${viewPrefix}"))
	}

	def 'deveria retornar status 400 quando parâmetro o "usuario" não for enviado'() {
		when:
		def resposta = mvc.perform(MockMvcRequestBuilders.get("/"))
	
		then:
		resposta.andExpect(MockMvcResultMatchers.status().is(400))
	}
}
