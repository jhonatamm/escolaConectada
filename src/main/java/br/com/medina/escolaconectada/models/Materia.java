package br.com.medina.escolaconectada.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.medina.escolaconectada.models.enums.BimestreEnum;
import br.com.medina.escolaconectada.models.enums.MateriaEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document
public class Materia implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)	
	private int codMateria;
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)	
	private int bimestre;
	private Integer totalAulas;
	private Integer ano;
	
	private List<Aluno> alunos = new ArrayList<Aluno>();
	
	@DBRef
	private Sala sala;
	
	public MateriaEnum getCodMateria() {
		return MateriaEnum.toEnum(codMateria);
	}
	public void setCodMateria(MateriaEnum materia) {
		this.codMateria = materia.getId();
	}
	public BimestreEnum getBimestre() {
		return BimestreEnum.toEnum(bimestre);
	}
	public void setBimestre(BimestreEnum bimestreEnum) {
		this.bimestre = bimestreEnum.getId();
	}
	
	
}
