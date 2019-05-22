package br.lostpets.project.spock

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

import javax.transaction.Transactional

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import br.lostpets.project.ProjectApplication
import br.lostpets.project.model.PetPerdido
import br.lostpets.project.model.Usuario
import br.lostpets.project.service.PetPerdidoService
import br.lostpets.project.service.UsuarioService
import spock.lang.Specification

@Transactional
@SpringBootTest(classes = ProjectApplication.class)
class PetPerdidoTest extends Specification {
	
	@Autowired
	PetPerdidoService petPerdidoService
	@Autowired
	UsuarioService usuarioService;
	
	def 'cadastra pet perdido COM Usuario'() {
				
		when:
			def usuario = new Usuario("mateus", "mateus@lost.com", "(11) 91234-1234", "(11) 1234-1234")
			usuarioService.salvarUsuario(usuario)			
			def petPerdido = new PetPerdido(usuario,"tobias", "12/12/2018", "Descrição perdido","Gato","C://Path","00.000.000","Latitude","Longitude")
			petPerdidoService.salvarPet(petPerdido)	
			
			def PetPerdido pet = petPerdidoService.encontrarUnicoPet(petPerdido.getIdAnimal())		
		then:
			petPerdido == pet
	}
	
	def 'não permitir cadastrar pet perdido SEM Usuario'() {
		
		when:
			def petPerdido = new PetPerdido(null,"tobias", "12/12/2018", "Descrição perdido","Gato","C://Path","00.000.000","Latitude","Longitude")
			petPerdidoService.salvarPet(petPerdido)
			
			def PetPerdido pet = petPerdidoService.encontrarUnicoPet(petPerdido.getIdAnimal())
		then:
			pet == null
	}
	
	def 'pegar todos animais perdidos ativos'() {
		
		when:
			
			def usuario = new Usuario("mateus", "mateus@lost.com", "(11) 91234-1234", "(11) 1234-1234")
			usuarioService.salvarUsuario(usuario)
		
			def petPerdido = new PetPerdido(usuario,"tobias", "12/12/2018", "Descrição perdido","Gato","C://Path","00.000.000","Latitude","Longitude")
			petPerdidoService.salvarPet(petPerdido)
			
			def petPerdido2 = new PetPerdido(usuario,"murilo", "12/12/2018", "Descrição perdido","Gato","C://Path","00.000.000","Latitude","Longitude")
			petPerdidoService.salvarPet(petPerdido2)
		
			List<PetPerdido> list = petPerdidoService.encontrarPetsAtivos()
		
		then:
			list.size() == 2
	}
	
	
	
	
}
