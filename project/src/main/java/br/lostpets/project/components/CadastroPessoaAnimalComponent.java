package br.lostpets.project.components;

import br.lostpets.project.model.PetPerdido;
import br.lostpets.project.model.Usuario;

public class CadastroPessoaAnimalComponent {	

 	private PetPerdido petPerdido;	
	private Usuario usuario;	

 	public CadastroPessoaAnimalComponent() {
		petPerdido = new PetPerdido();	
		usuario = new Usuario();	
	}	

 	public PetPerdido getPetPerdido() {	
		return petPerdido;	
	}	

 	public void setPetPerdido(PetPerdido petPerdido) {	
		this.petPerdido = petPerdido;	
	}	

 	public Usuario getUsuario() {	
		return usuario;	
	}	

 	public void setUsuario(Usuario usuario) {	
		this.usuario = usuario;	
	}	

 }	
