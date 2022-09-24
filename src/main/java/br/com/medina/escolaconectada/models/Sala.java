package br.com.medina.escolaconectada.models;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.medina.escolaconectada.models.enums.SerieEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document
public class Sala implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String nome;
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)	
	private int serie;
	
	private List<Materia> materias;
	
	@DBRef
	private Escola escola;

	public SerieEnum getSerie() {
		return SerieEnum.toEnum(serie);
	}

	public void setSerie(SerieEnum serie) {
		this.serie = serie.getId();
	}
	
	
	
	
	
}
