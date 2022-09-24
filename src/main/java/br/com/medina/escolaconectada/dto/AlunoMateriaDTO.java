package br.com.medina.escolaconectada.dto;

import br.com.medina.escolaconectada.models.Aluno;
import br.com.medina.escolaconectada.models.Materia;
import lombok.Data;

@Data
public class AlunoMateriaDTO {

	//private String id;
	private Double nota;
	private Double presenca;
	private Integer avaliacao;
	private String materiaId;
	
	public AlunoMateriaDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public AlunoMateriaDTO(Aluno aluno, Materia materia  ) {
		//this.id = aluno.getId();
		this.nota = aluno.getNota();
		this.presenca = aluno.getPresenca();
		this.avaliacao = aluno.getAvaliacao();
		this.materiaId = materia.getId();
	}
	
}
