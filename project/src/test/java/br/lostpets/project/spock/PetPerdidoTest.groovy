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
	def usuario
	def petPerdido
	
	def setup(){
		usuario = new Usuario("mateus", "mateus@lost.com", "(11) 91234-1234", "(11) 1234-1234")
		usuarioService.salvarUsuario(usuario)
		petPerdido = new PetPerdido(usuario,"tobias", "12/12/2018", "Descrição perdido","Gato","C://Path","00.000.000","Latitude","Longitude")
		petPerdidoService.salvarPet(petPerdido)
	}
	
	def 'cadastra pet perdido COM Usuario'() {
				
		when:
			def PetPerdido pet = petPerdidoService.encontrarUnicoPet(petPerdido.getIdAnimal())		
		then:
			petPerdido == pet
	}
	
	def 'pegar todos animais perdidos ativos'() {
		
		when:
			def petPerdido2 = new PetPerdido(usuario,"murilo", "12/12/2018", "Descrição perdido","Gato","C://Path","00.000.000","Latitude","Longitude")
			petPerdidoService.salvarPet(petPerdido2)
		
			List<PetPerdido> list = petPerdidoService.encontrarPetsAtivos()
		
		then:
			list.size() == 2
	}
	
	def 'não permitir cadastrar pet perdido NULL'() {
		
		when:
			PetPerdido petPerdido_ = null
			petPerdidoService.salvarPet(petPerdido_)
			
			def PetPerdido pet = petPerdidoService.encontrarUnicoPet(petPerdido_)
		then:
			pet == null
	}
	
	def 'não permitir cadastrar pet perdido SEM Usuario'() {
		
		when:
			def petPerdidoSemUsuario = new PetPerdido(null,"tobias", "12/12/2018", "Descrição perdido","Gato","C://Path","00.000.000","Latitude","Longitude")
			petPerdidoService.salvarPet(petPerdidoSemUsuario)
			
			def PetPerdido pet = petPerdidoService.encontrarUnicoPet(petPerdidoSemUsuario.getIdAnimal())
		then:
			pet == null
	}
	
	
	def 'não permitir cadastrar pet perdido SEM a data de desaparecido'(){
		when:
			def petPerdido_ = new PetPerdido(usuario,"tobias", null, "Descrição perdido","Gato","C://Path","00.000.000","Latitude","Longitude")
			petPerdidoService.salvarPet(petPerdido_)
		
			def PetPerdido pet = petPerdidoService.encontrarUnicoPet(petPerdido_.getIdAnimal())
		then:
			pet == null;
	}
	
	def 'não permitir cadastrar pet perdido SEM descrição do sumiço'(){
		when:
			def petPerdido_ = new PetPerdido(usuario,"tobias", "12/12/2018", null,"Gato","C://Path","00.000.000","Latitude","Longitude")
			petPerdidoService.salvarPet(petPerdido_)
		
			def PetPerdido pet = petPerdidoService.encontrarUnicoPet(petPerdido_.getIdAnimal())
		then:
			pet == null;
	}
	
	def 'não permitir cadastrar pet perdido SEM nome'(){
		when:
			def petPerdido_ = new PetPerdido(usuario,null, "12/12/2018", "Descrição do desaparecimento","Gato","C://Path","00.000.000","Latitude","Longitude")
			petPerdidoService.salvarPet(petPerdido_)
		
			def PetPerdido pet = petPerdidoService.encontrarUnicoPet(petPerdido_.getIdAnimal())
		then:
			pet == null;
	}
	
	def 'não permitir cadastrar pet perdido SEM categoria'(){
		when:
			def petPerdido_ = new PetPerdido(usuario,"tobias", "12/12/2018", "Descrição do desaparecimento",null,"C://Path","00.000.000","Latitude","Longitude")
			petPerdidoService.salvarPet(petPerdido_)
		
			def PetPerdido pet = petPerdidoService.encontrarUnicoPet(petPerdido_.getIdAnimal())
		then:
			pet == null;
	}
	
	def 'não permitir cadastrar pet perdido SEM cep'(){
		when:
			def petPerdido_ = new PetPerdido(usuario,"tobias", "12/12/2018", "Descrição do desaparecimento","Gatp","C://Path",null,"Latitude","Longitude")
			petPerdidoService.salvarPet(petPerdido_)
		
			def PetPerdido pet = petPerdidoService.encontrarUnicoPet(petPerdido_.getIdAnimal())
		then:
			pet == null;
	}
	
	
}
