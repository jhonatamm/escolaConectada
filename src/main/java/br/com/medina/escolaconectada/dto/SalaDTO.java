package br.com.medina.escolaconectada.dto;

import java.io.Serializable;

import br.com.medina.escolaconectada.models.Sala;
import lombok.Data;

@Data
public class SalaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4704342346713575592L;
	
	
	private String id;
	private String nome;
	private String escolaId;
	private int serie;
	private String serieNome;
	
	public SalaDTO() {}
	
	public SalaDTO(Sala sala) {
		this.id = sala.getId();
		this.escolaId = sala.getEscola().getId();
		this.nome = sala.getNome();
		this.serie = sala.getSerie().getId();
		this.serieNome = sala.getSerie().getDescription();
	}
	
	
}
