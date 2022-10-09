package br.com.medina.escolaconectada.models;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
@CompoundIndexes(value = {
@CompoundIndex(name = "cidade_nome", def = "{'cidade.id' : 1, 'nome': 1}", unique = true)})
public class Escola implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String nome;
	private boolean processed = false;
	private Map<Integer,Map<String,Double>> escolaGeneralMetrics = new LinkedHashMap<Integer, Map<String,Double>>();
	
	
	@DBRef
	private Cidade cidade;

}
