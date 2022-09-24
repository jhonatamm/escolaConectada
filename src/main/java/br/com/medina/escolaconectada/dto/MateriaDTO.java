package br.com.medina.escolaconectada.dto;

import java.io.Serializable;

import br.com.medina.escolaconectada.models.Materia;
import lombok.Data;

@Data
public class MateriaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4704342346713575591L;
	
	
	private String id;
	private int codMateria;
	private String nomeMateria;
	private int bimestre;
	private String bimestreNome;
	private int totalAulas;
	private Integer ano;
	private Integer alunoCount;
	private String salaNome;
	private String salaId;
	
	public MateriaDTO() {}
	
	public MateriaDTO(Materia materia) {
		this.id = materia.getId();
		this.codMateria = materia.getCodMateria().getId();
		this.nomeMateria =  materia.getCodMateria().getDescription();
		this.bimestre = materia.getBimestre().getId();
		this.bimestreNome = materia.getBimestre().getDescription();
		this.totalAulas = materia.getTotalAulas();
		this.ano = materia.getAno();
		this.alunoCount = materia.getAlunos().size();
		if(materia.getSala() != null ) {
			this.salaNome = materia.getSala().getNome();
			this.salaId = materia.getSala().getId();
		}
	}
	
	
}

