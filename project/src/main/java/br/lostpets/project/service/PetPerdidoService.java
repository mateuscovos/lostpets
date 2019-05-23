package br.lostpets.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import br.lostpets.project.model.PetPerdido;
import br.lostpets.project.repository.PetPerdidoRepository;

@Service
public class PetPerdidoService {

	@Autowired
	PetPerdidoRepository petPerdidoRepository;

	public List<PetPerdido> encontrarPetsAtivos() {
		return petPerdidoRepository.getAtivos();
	}

	public PetPerdido encontrarUnicoPet(int id) {
		return petPerdidoRepository.getAtivosByIdAnimal(id);
	}
	
	public PetPerdido encontrarUnicoPet(PetPerdido petPerdido) {
		if(petPerdido == null) { return null; }
		return petPerdidoRepository.getAtivosByIdAnimal(petPerdido.getIdAnimal());
	}

	public void salvarPet(PetPerdido petPerdido) {		
		if (!this.isValidAttributes(petPerdido)) { return; }
	
		petPerdidoRepository.save(petPerdido);	
	}

	public List<PetPerdido> encontrarTodos() {
		return petPerdidoRepository.findAll();
	}

	private boolean isValidAttributes(PetPerdido petPerdido) {

		if (petPerdido == null) { 
			return false; 
		}
		if (petPerdido.getIdUsuario() == null) { 
			return false; 
		}		
		if(petPerdido.getIdUsuario().getIdPessoa() == 0) {
			return false; 
		}
		if (this.isEmptyOrNull(petPerdido.getDataPerdido())) {
			return false; 
		}
		if (this.isEmptyOrNull(petPerdido.getDescricao())) {
			return false; 
		}
		if (this.isEmptyOrNull(petPerdido.getNomeAnimal())) {
			return false; 
		}
		if (this.isEmptyOrNull(petPerdido.getTipoAnimal())) {
			return false; 
		}
		if (this.isEmptyOrNull(petPerdido.getCep())) {
			return false; 
		}

		return true; 
	}

	private boolean isEmptyOrNull(String text) {
		if (text == null) {
			return true;
		}
		if (text.isEmpty()) {
			return true;
		}
		return false;
	}

}
