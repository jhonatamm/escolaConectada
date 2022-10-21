package br.com.medina.escolaconectada.dto;

import java.io.Serializable;
import java.util.Date;

import br.com.medina.escolaconectada.models.metrics.EscolaMetrics;
import lombok.Data;

@Data
public class EscolaMetricsDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String id;
	
	private Date dtCreated;
	private Date dtUpdated;
	
    private String escolaId;
    private String escolaName;
	
	private String materiaDescription;
	private int materiaCod;
	private String materiaEscolaName;
	
	private int ano;
	private String bimestreName;
	private int bimestreCod;
	private String anoBimestre;
	
	private Double mPresenca;
	private Double mNota; 
	private Double mAvaliacao;
	
	public EscolaMetricsDTO() {}
	
	public EscolaMetricsDTO(EscolaMetrics escolaMetrics) {
		this.id = escolaMetrics.getId();
		this.dtCreated = escolaMetrics.getDtCreated();
		this.dtUpdated = escolaMetrics.getDtUpdated();
		this.escolaId = escolaMetrics.getEscola().getId();
		this.escolaName =  escolaMetrics.getEscola().getNome();
		this.materiaDescription = escolaMetrics.getMateriaDescription();
		this.materiaCod = escolaMetrics.getMateriaCod();
		this.materiaEscolaName = escolaMetrics.getMateriaDescription() +" - "+ escolaMetrics.getEscola().getNome();
		this.ano = escolaMetrics.getAno();
		this.bimestreName = escolaMetrics.getBimestreName();
		this.bimestreCod = escolaMetrics.getBimestreCod();
		this.anoBimestre =escolaMetrics.getAnoBimestre();
		this.mPresenca = escolaMetrics.getMPresenca();
		this.mAvaliacao = escolaMetrics.getMAvaliacao();
		this.mNota = escolaMetrics.getMNota();
	}

}
