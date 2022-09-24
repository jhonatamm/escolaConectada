package br.com.medina.escolaconectada.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class Aluno implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	//private String id;
	private Double nota;
	private Double presenca;
	private Integer avaliacao;
	 
}
