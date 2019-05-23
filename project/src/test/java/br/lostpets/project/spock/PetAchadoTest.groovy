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
import br.lostpets.project.model.AnimaisAchados
import br.lostpets.project.model.PetPerdido
import br.lostpets.project.model.Usuario
import br.lostpets.project.repository.AnimaisAchadosRepository
import br.lostpets.project.repository.PetPerdidoRepository
import br.lostpets.project.repository.UsuarioRepository
import br.lostpets.project.service.PetPerdidoService
import br.lostpets.project.service.UsuarioService
import spock.lang.Specification
	
@Transactional
@SpringBootTest(classes = ProjectApplication.class)
class PetAchadoTest extends Specification {
	
	@Autowired
	AnimaisAchadosRepository animaisAchadosRepository;
	@Autowired
	PetPerdidoRepository petPerdidoRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	
	//private AnimaisAchados achados
	def usuario;
	def petPerdido;
	def achados;
	
	def setup(){
		usuario = new Usuario("mateus", "mateus@lost.com", "(11) 91234-1234", "(11) 1234-1234")
		usuarioRepository.save(usuario)
		petPerdido = new PetPerdido(usuario,"tobias", "12/12/2018", "Descrição perdido","Gato","C://Path","00.000.000","Latitude","Longitude")
		petPerdidoRepository.save(petPerdido)
		achados = new AnimaisAchados(usuario, petPerdido, new Date(), 10, "latitude", "longitude");
		animaisAchadosRepository.save(achados);
	}
	
	def 'cadastra pet achado'() {
		
		when:					
			def AnimaisAchados pet = animaisAchadosRepository.getOne(achados.getId());				
		then:
				achados == pet
	}
}

