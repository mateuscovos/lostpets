package br.lostpets.project.spock

import org.springframework.validation.BindingResult
import org.springframework.validation.BindingResultUtils

import javax.transaction.Transactional

import org.jboss.jandex.TypeTarget.Usage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.servlet.ModelAndView

import br.lostpets.project.controller.LoginController
import br.lostpets.project.model.Usuario
import br.lostpets.project.service.HistoricoAcessoLog
import br.lostpets.project.service.UsuarioService
import spock.lang.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
class SpockLoginControllerTest extends Specification{

	private Usuario usuario;
	@Autowired
	private UsuarioService usuarioService;
	MockMvc agenteREST

	def UrlPadrao="http://localhost:8080"
	def setup() {
		UsuarioService usuarioService=Mock()
		HistoricoAcessoLog historicoAcessoLog=Mock()
		agenteREST = MockMvcBuilders.standaloneSetup(new LoginController(usuarioService,historicoAcessoLog)).build()
	}

	def 'verificar se usuario consta na base de dados para acessar'() {
		given:
		usuario = new Usuario("nome", "telefoneFixo", "telefoneCelular", "email", "senha", "idImagem", "cep", "rua", "bairro", "cidade", "uf", "latitude", "longitude")
		usuarioService.salvarUsuario(usuario)
		expect:
		usuarioService.emailSenha(usuario.getEmail(), usuario.getSenha()) != null
	}

	def 'realizar GET com FRACASSO'(){

		given:" a url testada errada (sem letras maiuculas)"
		def urlTestada="${UrlPadrao}/lostpets"

		when:" performado GET"
		def resposta=agenteREST.perform(get(urlTestada))
				.andReturn()
				.getResponse()

		then:" o status da requisição http é OK"
		resposta.status == 404

	}

	def 'realizar GET com SUCESSO'(){

		given:" a url testada correta"
		def urlTestada="${UrlPadrao}/LostPets"

		when:" performado GET"
		def resposta=agenteREST.perform(get(urlTestada))
				.andReturn()
				.getResponse()

		then:" o status da requisição http é OK"
		resposta.status == 200

	}


	def 'realizar POST com SUCESSO'(){

		given:" a url testada correta"
		def urlTestada="${UrlPadrao}/Dashboard"
		Usuario usuario = Mock()

		when:" performado POST"
		def resposta=agenteREST.perform(
				post(urlTestada)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.sessionAttr("usuario", usuario)
				)
				.andReturn()
				.getResponse()

		then:" o status da requisição http é 302"
		resposta.status == 302
	}

	def 'realizar POST em lugar que não aceita'(){
		
		given:" a url testada correta porem não aceita POST"
		def urlTestada="${UrlPadrao}/LostPets"

		when:" performado POST"
		def resposta=agenteREST.perform(
				post(urlTestada)
				)
				.andReturn()
				.getResponse()

		then:" o status da requisição http é (405) Método não permitido"
		resposta.status == 405
	}

	def 'verificar campos email e senha é null'(){
		usuario = new Usuario("nome", "telefoneFixo", "telefoneCelular", null, null, "idImagem", "cep", "rua", "bairro", "cidade", "uf", "latitude", "longitude")
		expect:
		usuario.getEmail() == null || usuario.getSenha() == null
	}



}
