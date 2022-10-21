package br.com.medina.escolaconectada.dto;

import java.io.Serializable;
import java.util.Date;

import br.com.medina.escolaconectada.models.metrics.BimestreMetrics;
import lombok.Data;

@Data
public class BimestreMetricsDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private Date dtCreated;
	private Date dtUpdated;

	private String materiaId;
	private String materiaDescription;
	private Integer materiaCod;
	private String salaId;
	private Integer serieId;
	private String serie;

	private Double mPresenca;
	private Double mNota;
	private Double mAvaliacao;
	
	private String escolaId;
	private String escolaName;
	private String materiaSerieEscolaName;

	private String bimestreName;
	private Integer ano;
	private String anoBimestre;

	public BimestreMetricsDTO() {

	};

	public BimestreMetricsDTO(BimestreMetrics bimestreMetrics) {
		this.id= bimestreMetrics.getId();
		this.dtCreated = bimestreMetrics.getDtCreated();
		this.dtUpdated = bimestreMetrics.getDtUpdated();
		this.materiaId = bimestreMetrics.getMateria().getId();
		this.salaId = bimestreMetrics.getMateria().getSala().getId();
		this.serie = bimestreMetrics.getMateria().getSala().getSerie().getDescription();
		this.serieId = bimestreMetrics.getMateria().getSala().getSerie().getId();
		this.escolaId = bimestreMetrics.getMateria().getSala().getEscola().getId();
		this.escolaName = bimestreMetrics.getMateria().getSala().getEscola().getNome();
		this.materiaSerieEscolaName = bimestreMetrics.getMateria().getCodMateria().getDescription() + " - " + bimestreMetrics.getMateria().getSala().getSerie().getDescription() + " - " + bimestreMetrics.getMateria().getSala().getEscola().getNome();
		this.mNota= bimestreMetrics.getMNota();
		this.mPresenca = bimestreMetrics.getMPresenca();
		this.mAvaliacao = bimestreMetrics.getMAvaliacao();
		this.bimestreName = bimestreMetrics.getMateria().getBimestre().getDescription();
		this.ano = bimestreMetrics.getMateria().getAno();
		this.anoBimestre = bimestreMetrics.getMateria().getAno() +"-"+bimestreMetrics.getMateria().getBimestre().getId();
		this.materiaDescription= bimestreMetrics.getMateria().getCodMateria().getDescription();
		this.materiaCod = bimestreMetrics.getMateria().getCodMateria().getId();
	};

}
