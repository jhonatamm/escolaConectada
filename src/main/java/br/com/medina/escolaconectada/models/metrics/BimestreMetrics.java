package br.com.medina.escolaconectada.models.metrics;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.medina.escolaconectada.models.Materia;
import lombok.Data;

@Data
@Document
public class BimestreMetrics implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private Date dtCreated;
	private Date dtUpdated;
	
	@JsonIgnore
    @DBRef
    private Materia materia;
	
	private Double mPresenca;
	private Double mNota; 
	private Double mAvaliacao;
	public BimestreMetrics() {
		this.dtCreated = new Date();
		this.dtUpdated = new Date();
	} 
	
	

}
