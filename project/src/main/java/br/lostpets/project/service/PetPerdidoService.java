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
	public List<PetPerdido> encontrarPetsAtivos(){
		return petPerdidoRepository.getAtivos();
	}
	
	public PetPerdido encontrarUnicoPet(int id) {
		return petPerdidoRepository.getAtivosByIdAnimal(id);
	}

	public void salvarPet(PetPerdido petPerdido) {
		if(petPerdido.getIdUsuario() != null) {
			if(petPerdido.getIdUsuario().getIdPessoa() != 0) {
				petPerdidoRepository.save(petPerdido);
			}
		}
	}

	public List<PetPerdido> encontrarTodos() {
		return petPerdidoRepository.findAll();
	}
	
}
