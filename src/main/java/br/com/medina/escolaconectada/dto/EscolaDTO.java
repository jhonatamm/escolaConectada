package br.com.medina.escolaconectada.dto;

import java.io.Serializable;
import java.util.Map;

import br.com.medina.escolaconectada.models.Escola;
import lombok.Data;

@Data
public class EscolaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4704342346713575590L;
	
	
	private String id;
	private String nome;
	private String cidadeId;
	private Map<Integer,Map<String,Double>> generalMetrics;
	
	public EscolaDTO() {}
	
	public EscolaDTO(Escola escola) {
		this.id = escola.getId();
		this.cidadeId = escola.getCidade().getId();
		this.nome = escola.getNome();
		generalMetrics = escola.getEscolaGeneralMetrics();
	}
	
	
}
