package br.lostpets.project.spock

import br.lostpets.project.model.Usuario
import spock.lang.Specification

class SpockLoginTest extends Specification{

	private Usuario usuario = new Usuario();

	def 'verificar acesso corretamente'(){
		given:
		usuario = new Usuario("nome", "telefoneFixo", "telefoneCelular", "teste@teste.com", "1234Teste", "idImagem", "cep", "rua", "bairro", "cidade", "uf", "latitude", "longitude")
		when:
		boolean x = usuario.getEmail() == 'teste@teste.com'
		boolean y = usuario.getSenha() == '1234Teste'
		then:
		x == true && y == true
	}

	def 'verificar campos email e senha é null'(){
			usuario = new Usuario("nome", "telefoneFixo", "telefoneCelular", null, null, "idImagem", "cep", "rua", "bairro", "cidade", "uf", "latitude", "longitude")
		expect:
			usuario.getEmail() == null || usuario.getSenha() == null
	}
}
